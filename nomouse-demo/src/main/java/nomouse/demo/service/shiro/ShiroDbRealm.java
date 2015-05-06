package nomouse.demo.service.shiro;

import nomouse.demo.entity.User;
import nomouse.demo.service.AccountService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShiroDbRealm extends AuthorizingRealm {

    @Autowired
    protected AccountService accountService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return super.supports(token);
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = accountService.findUserByName(token.getUsername());
        if (user != null) {
            return new SimpleAuthenticationInfo(
                    new ShiroUser(user.getId(), user.getUsername()),
                    user.getPassword(),
                    getName());
        } else {
            return null;
        }
    }

    /**
     * 权限控制
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
