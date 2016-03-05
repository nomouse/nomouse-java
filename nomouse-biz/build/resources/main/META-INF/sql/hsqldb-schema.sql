create table USER (
    USER_ID varchar(25) not null,
    PASSWORD varchar(25)  not null,
    ROLES varchar(100),

    constraint PK_USER primary key (USER_ID)
);

create table ACCOUNT (
    USER_ID varchar(25) not null,
    EMAIL varchar(80),
    FIRST_NAME varchar(80),
    LAST_NAME varchar(80),
    STATUS varchar(2),
    ADDRESS_1 varchar(80),
    ADDRESS_2 varchar(40),
    CITY varchar(80),
    STATE varchar(80),
    ZIP varchar(20),
    COUNTRY varchar(20),
    PHONE varchar(80),
    CREDIT_CARD_NUMBER varchar(50),
    CREDIT_CARD_TYPE varchar(20),
    CREDIT_CARD_EXPIRY date,

    constraint PK_ACCOUNT primary key (USER_ID)
);

create table PROFILE (
    USER_ID varchar(25) not null,
    LANGUAGE_PREFERENCE varchar(80),
    FAVORITE_CATEGORY_ID varchar(30),
    LIST_OPTION int,
    BANNER_OPTTION int,

    constraint PK_PROFILE primary key (USER_ID)
);


create table supplier (
    suppid int not null,
    name varchar(80) null,
    status varchar(2) not null,
    addr1 varchar(80) null,
    addr2 varchar(80) null,
    city varchar(80) null,
    state varchar(80) null,
    zip varchar(5) null,
    phone varchar(80) null,
    constraint pk_supplier primary key (suppid)
);



create table bannerdata (
    favcategory varchar(80) not null,
    bannername varchar(255)  null,
    constraint pk_bannerdata primary key (favcategory)
);

create table orders (
      orderid int not null,
      userid varchar(80) not null,
      orderdate date not null,
      shipaddr1 varchar(80) not null,
      shipaddr2 varchar(80) null,
      shipcity varchar(80) not null,
      shipstate varchar(80) not null,
      shipzip varchar(20) not null,
      shipcountry varchar(20) not null,
      billaddr1 varchar(80) not null,
      billaddr2 varchar(80)  null,
      billcity varchar(80) not null,
      billstate varchar(80) not null,
      billzip varchar(20) not null,
      billcountry varchar(20) not null,
      courier varchar(80) not null,
      totalprice decimal(10,2) not null,
      billtofirstname varchar(80) not null,
      billtolastname varchar(80) not null,
      shiptofirstname varchar(80) not null,
      shiptolastname varchar(80) not null,
      creditcard varchar(80) not null,
      exprdate varchar(7) not null,
      cardtype varchar(80) not null,
      locale varchar(80) not null,
      constraint pk_orders primary key (orderid)
);

create table orderstatus (
      orderid int not null,
      orderitemid int not null,
      timestamp date not null,
      status varchar(2) not null,
      constraint pk_orderstatus primary key (orderid, orderitemid)
);

create table orderitem (
      orderid int not null,
      orderitemid int not null,
      itemid varchar(10) not null,
      quantity int not null,
      unitprice decimal(10,2) not null,
      constraint pk_orderitem primary key (orderid, orderitemid)
);

create table category (
	catid varchar(10) not null,
	name varchar(80) null,
	logo varchar(80) null,
	descn varchar(255) null,
	constraint pk_category primary key (catid)
);

create table product (
    productid varchar(10) not null,
    category varchar(10) not null,
    name varchar(80) null,
    logo varchar(80) null,
    descn varchar(255) null,
    constraint pk_product primary key (productid),
        constraint fk_product_1 foreign key (category)
        references category (catid)
);

create index productCat on product (category);
create index productName on product (name);

create table item (
    itemid varchar(10) not null,
    productid varchar(10) not null,
    listprice decimal(10,2) null,
    unitcost decimal(10,2) null,
    supplier int null,
    status varchar(2) null,
    attr1 varchar(80) null,
    attr2 varchar(80) null,
    attr3 varchar(80) null,
    attr4 varchar(80) null,
    attr5 varchar(80) null,
    constraint pk_item primary key (itemid),
        constraint fk_item_1 foreign key (productid)
        references product (productid),
        constraint fk_item_2 foreign key (supplier)
        references supplier (suppid)
);

create index itemProd on item (productid);

create table inventory (
    itemid varchar(10) not null,
    qty int not null,
    constraint pk_inventory primary key (itemid)
);

CREATE TABLE sequence
(
    name               varchar(30)  not null,
    nextid             int          not null,
    constraint pk_sequence primary key (name)
);
