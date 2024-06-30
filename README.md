# Chart Server

## 📖 Description
한국 투자 증권 API를 활용하여 주식 데이터 관련된 서비스를 처리 합니다.
스케줄링을 통해 데이터를 수집하고 이를 클라이언트에게 제공합니다. 

## ⚙ Function
1. 주식 차트(년, 월, 주, 일, 분) 데이터 제공
2. 호가, 등락, 지수(KOSPI, KOSDAQ), 투자자 데이터 제공
   
## 🔧 Stack
 - **Language** : Java 17
 - **Library & Framework** : Spring Boot 3.2.5
 - **Database** : Mongo, Redis
 - **ODM** : Spring Data MongoDB
 - **Deploy** : AWS EC2 / Jenkins
 - **Dependencies** : Spring Web, Mongo, Redis, Validation, Lombok, Model Mapper, Swagger, JWT, Eureka, Kakfa, Spring Retry, Spring Aspects

## 🔧 Architecture
- **Design Patter** : Layered Architecture
- **Micro Service Architecture** : Spring Cloud
- **Event-Driven Architecture** : Kafka

## 👨‍👩‍👧‍👦 Developer
*  **강성욱** ([KangBaekGwa](https://github.com/KangBaekGwa))
*  **김도형** ([ddohyeong](https://github.com/ddohyeong))
*  **박태훈** ([hoontaepark](https://github.com/hoontaepark))
