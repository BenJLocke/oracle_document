#简单操作，支持事务

https://blog.csdn.net/weixin_34007291/article/details/89567494
https://blog.csdn.net/wiselyman/article/details/88346521
https://www.sohu.com/a/256218825_411876


#和Mogodb的比较

https://blog.csdn.net/weixin_34120274/article/details/92067918
PG的商业版EnterpriseDB公司在2014.9月发布了一份针对MongoDB v2.6 to Postgres v9.4 beta的对比报告（如果其对比MongoDB 3.0版本可能测试结果会有差别）， 简单翻译其测试报告结果如下：
5000万文档型数据查询、加载、插入时：
1. 大量数据的加载，PG比MongoDB快2.1倍
2. 同样的数据量，MongoDB的占用空间是PG的1.33倍。
3. Insert操作MongoDB比PG慢3倍
4. Select操作MongoDB比PG慢2.5倍。


#事物处理

```
ben=# begin;
BEGIN
ben=# INSERT INTO test_json VALUES ('{"hello":"hello-value", "wolrd":"world-value"}');
INSERT 0 1
ben=#  SELECT * from test_json ;
                      hello
--------------------------------------------------
 {"wolrd": "world-value"}
 {"hello": "hello-value", "wolrd": "world-value"}
 {"hello": "hello-value", "wolrd": "world-value"}
(3 行记录)


ben=# rollback;
ROLLBACK
ben=#  SELECT * from test_json ;
                      hello
--------------------------------------------------
 {"wolrd": "world-value"}
 {"hello": "hello-value", "wolrd": "world-value"}
(2 行记录)
```
