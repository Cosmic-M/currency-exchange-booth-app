# Exchange Rate App

<h3>To launch app:</h3>
- pull this file<br>
- start docker on your local machine<br>
- open terminal and start command: docker-compose up<br>
- open browser and write: http://localhost:6868/swagger-ui/#/<br>
- H2 connected, please follow the link: http://localhost:8080/h2-console ##password -> root##
- I added some scripts for creating tables and filling them with data, so...<br>
everything prepared to working<br>

<h3>Project description:</h3>
Exchange rate app reflects server working part and works in realtime to provide exchange rate<br>
point with base functionality to work. There aren't so many endpoints, thus we can view quickly on it:<br>
GET: /exchange-rate/open-work-day : implements loading actual data from outer API concerning currency's<br>
exchange rates. You can invoke this operation during the day to renew rate (in this way it's just rewrite<br>
current rate obtained earlier this day). Mind you, operator can forger to invoke this operation in the<br>
beginning of the day, so crone jobs called to fix such little sins.
GET: /report : just returning information about daily operations in short form, like how many operations<br>
were per currency and total sum.
GET: /ccy-period : detailed data, actually its returning all information concerning deal. I implemented<br>
pagination and sorting, so you can work comfortably with big data amount.
POST: /deal/create : create "raw" deal (with status "NEW"), and returning an amount to pay for buying<br>
currency, and otp password to client phone, so don't forget to point valid telephone number.
POST: /deal/validate-otp : deal confirmation.
DELETE: /deal/delete : if need, you can delete deals with status "NEW".

<h3>In this APP were used such technologies like:</h3>
- org.apache.maven, version 4.0.0<br>
- java, version 17<br>
- org.hibernate<br>
- spring boot<br>
- liquibase<br>
- twilio<br>
- junit<br>
