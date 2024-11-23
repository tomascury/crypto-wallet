# Crypto Wallet

### Description
The system is responsible for managing crypto wallets.
It maintains the current prices of the assets and provides performance data per wallet.

### Running locally
1. Create the dependencies: <br/>
``docker compose --file docker/docker-compose.yml up -d``
2. Install [Java 23](https://www.oracle.com/java/technologies/downloads/)
2. Make sure to use the ``spring_profiles_active=dev``
   3. ```mvn spring-boot:run -Dspring-boot.run.profiles=dev```
3. For local testing import [collection](./test/crypto-wallet_session_2024-11-23.json) into any API client tool (e.g. Postman, Insomnia)