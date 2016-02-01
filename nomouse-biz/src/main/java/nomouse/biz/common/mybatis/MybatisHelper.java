package nomouse.biz.common.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * mybatis帮助类
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class MybatisHelper implements Interceptor {

    private static ThreadLocal<MybatisPage> localThreadPage = new ThreadLocal<>();

    public static <E> MybatisPage<E> page(int pageNum, int pageSize, String orderBy, boolean count, SqlExecutor executor) {
        MybatisPage<E> page = new MybatisPage(pageNum, pageSize, orderBy, count);
        localThreadPage.set(page);
        executor.execute();
        localThreadPage.remove();
        return page;
    }

    public static <E> MybatisPage<E> page(int pageNum, int pageSize, boolean count, SqlExecutor executor) {
        return page(pageNum, pageSize, null, count, executor);
    }

    public static <E> MybatisPage<E> page(int pageNum, int pageSize, String orderBy, SqlExecutor executor) {
        return page(pageNum, pageSize, orderBy, false, executor);
    }

    public static <E> MybatisPage<E> page(int pageNum, int pageSize, SqlExecutor executor) {
        return page(pageNum, pageSize, null, false, executor);
    }

    public static <E> MybatisPage<E> count(SqlExecutor executor) {
        return page(0, 0, null, true, executor);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MybatisPage page = localThreadPage.get();
        if (page != null) {
            //当前环境 MappedStatement，BoundSql，及sql取得
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            Object parameterObject = invocation.getArgs()[1];
            //如果包含分页参数
            BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
            String originalSql = boundSql.getSql().trim();

            //得到总数量
            if (page.isCount()) {
                String countSql = "SELECT COUNT(*) FROM (" + originalSql + ") _FOR_TOTAL";
                Connection connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
                PreparedStatement countStmt = connection.prepareStatement(countSql);
                BoundSql countBS = copyFromBoundSql(mappedStatement, boundSql, countSql);
                DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, countBS);
                parameterHandler.setParameters(countStmt);
                ResultSet rs = countStmt.executeQuery();
                int total = 0;
                if (rs.next()) {
                    total = rs.getInt(1);
                }
                rs.close();
                countStmt.close();
                connection.close();

                page.setTotal(total);
            }


            boolean needIntercept = false;
            StringBuffer sqlSuffix = new StringBuffer(originalSql);

            //排序
            String orderBy = page.getOrderBy();
            if (orderBy != null && !"".equals(orderBy)) {
                sqlSuffix.append(" ORDER BY ").append(page.getOrderBy());
                needIntercept = true;
            }

            //分页
            if (page.getPageSize() > 0) {
                sqlSuffix.append(" LIMIT ").append(page.getOffset()).append(",").append(page.getPageSize());
                needIntercept = true;
            }

            //生成新查询
            if (needIntercept) {
                BoundSql newBoundSql = copyFromBoundSql(mappedStatement, boundSql, sqlSuffix.toString());
                MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
                invocation.getArgs()[0] = newMs;
            }
            List result = (List) invocation.proceed();
            page.setList(result);

            return result;
        } else {
            return invocation.proceed();
        }
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(),
                ms.getId(), newSqlSource, ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
//        builder.keyProperty(ms.getKeyProperties());
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    /**
     * 复制BoundSql对象
     */
    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    public class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

    public static interface SqlExecutor {
        public void execute();
    }
}
