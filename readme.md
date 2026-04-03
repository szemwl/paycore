# Paycore

Payment service for processing cashless transfer orders.

## Tech stack

- Java 21
- Spring Boot 3.3.5
- Maven
- Spring Web
- Spring Data JPA
- PostgreSQL
- Apache Kafka
- Lombok
- JUnit 5

---

src  
├── main  
│   └── java  
│       └── org.paycore.payment  
│  
└── test  
    ├── java  
    │   └── org.paycore.payment  
    │       ├── unit  
    │       │   ├── service  
    │       │   │   └── OrderServiceTest.java  
    │       │   ├── mapper  
    │       │   └── validator  
    │       │  
    │       ├── integration  
    │       │   ├── api  
    │       │   │   ├── BaseApiTest.java  
    │       │   │   ├── OrderApiSmokeTest.java  
    │       │   │   ├── OrderApiValidationTest.java  
    │       │   │   ├── OrderApiNegativeTest.java  
    │       │   │   └── OrderStatusApiTest.java  
    │       │   │  
    │       │   ├── db  
    │       │   │   └── OrderRepositoryTest.java  
    │       │   │  
    │       │   ├── kafka  
    │       │   │   ├── OrderStatusKafkaConsumerTest.java  
    │       │   │   └── OrderEventProducerTest.java  
    │       │   │  
    │       │   └── service  
    │       │       └── OrderServiceIntegrationTest.java  
    │       │  
    │       ├── e2e  
    │       │   └── ui  
    │       │       └── PaymentFlowUiTest.java  
    │       │  
    │       ├── steps  
    │       │   ├── OrderSteps.java  
    │       │   └── KafkaSteps.java  
    │       │  
    │       ├── data  
    │       │   └── OrderTestData.java  
    │       │  
    │       ├── spec  
    │       │   ├── RequestSpec.java  
    │       │   └── ResponseSpecs.java  
    │       │  
    │       ├── assertion  
    │       │   └── OrderAssertions.java  
    │       │  
    │       ├── util  
    │       │   └── TestUtils.java  
    │       │  
    │       └── config  
    │           └── TestConfig.java  
    │  
    └── resources  
        ├── application-test.yml  
        ├── testdata  
        │   └── orders  
        │       └── valid-order.json  
        └── sql  
            ├── cleanup.sql  
            └── seed.sql  
