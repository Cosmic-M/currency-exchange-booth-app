# Exchange Rate App

<h3>To launch app:</h3>
- pull this project<br>
- follow the link https://console.twilio.com/ and pass registration<br>
- in your Intellij open src/main/resources/application.properties and assign <br>
TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN, TWILIO_TRIAL_NUMBER in appropriate variables.<br>
- start docker on your local machine<br>
- open terminal and start command: docker run -ti --rm -e TZ=Europe/Kiev -p 8085:8080 exchange-rate-app<br>
- in your browser open http://localhost:8085/swagger-ui/#/ and http://localhost:8085/h2-console <br>
(JDBC URL: jdbc:h2:mem:currency and User Name: root)<br>
- I added some scripts for creating tables and filling them with data, so...everything prepared to working<br>

<h3>Project description:</h3>
Exchange rate app reflects server working part and works in realtime to provide exchange rate<br>
point with base functionality to work. There aren't so many endpoints, thus we can view quickly on it:<br>
GET: /exchange-rate/open-work-day : implements loading actual data from outer API concerning currency's<br>
exchange rates. You can invoke this operation during the day to renew rate (in this way it's just rewrite<br>
current rate obtained earlier this day). Mind you, operator can forger to invoke this operation in the<br>
beginning of the day, so crone jobs called to fix such a little sin.<br>
GET: /report : just returning information about daily operations in short form, like how many operations<br>
were per currency and total sum.<br>
GET: /ccy-period : detailed data, actually its returning all information concerning deal. I implemented<br>
pagination and sorting, so you can work comfortably with big data amount.<br>
POST: /deal/create : create "raw" deal (with status "NEW"), and returning an amount to pay for buying<br>
currency, and otp password to client phone, so don't forget to point valid phone number.<br>
POST: /deal/validate-otp : deal confirmation.<br>
DELETE: /deal/delete : if need, you can delete deals with status "NEW".<br>

<h3>In this APP were used such technologies like:</h3>
- org.apache.maven, version 4.0.0<br>
- java, version 17<br>
- org.hibernate<br>
- spring boot<br>
- liquibase<br>
- twilio<br>
- junit<br>
