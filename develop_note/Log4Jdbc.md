# Log4Jdbc 설정하기

맵퍼에서 실행하는 쿼리에 대해서 로그를 찍고 싶은데 인터페이스로 되어있어서 어디다가 로그를 찍어야 하는지를 모르겠다.

구글에 검색해보니 셋팅하는 방법이 있어서 한참을 고생해서 셋팅을 완성 할 수 있었다.

https://www.leafcats.com/45 도움 받은 사이트

build.gradle

```groovy
compile group: 'org.bgee.log4jdbc-log4j2', name: 'log4jdbc-log4j2-jdbc4', version: '1.16'
```

main/resources/application.properties

```
spring.datasource.driver-class-name=net.sf.log4jdbc.sql.jdbcapi.DriverSpy
spring.datasource.url = jdbc:log4jdbc:mysql://sarang628.iptime.org:3306/TravelBuddy?useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC
spring.datasource.username = travelbuddy
spring.datasource.password = travelbuddy!@#
server.port=8090
```

main/resources/log4jdbc.log4j2.Properties

```
log4jdbc.spylogdelegator.name=net.sf.log4jdbc.log.slf4j.Slf4jSpyLogDelegator
log4jdbc.dump.sql.maxlinelength=0
```

main/resources/logback.xml

```
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyyMMdd HH:mm:ss.SSS} [%thread] %-3level %logger{5} - %msg %n</pattern>
        </encoder>
    </appender>

    <logger name="jdbc" level="OFF"/>

    <logger name="jdbc.sqlonly" level="OFF"/>
    <logger name="jdbc.sqltiming" level="DEBUG"/>
    <logger name="jdbc.audit" level="OFF"/>
    <logger name="jdbc.resultset" level="OFF"/>
    <logger name="jdbc.resultsettable" level="DEBUG"/>
    <logger name="jdbc.connection" level="OFF"/>

    <root level="INFO">
        <appender-ref ref="STDOUT"/>
    </root>

</configuration>
```

위와같이 셋팅을 해주고 빌드를 하면 로그가 찍힌다!