# Nyquist Bot

Chat-bot for Twitch platform based on Spring framework.

## Release

`
0.1a
`

## Technologies

- Spring Boot 3.1.1
- Kotlin 1.8.22

## Requirements

- JDK 17
- Gradle 7.4.1

## Application execution

1. Make sure your JAVA_HOME paths to jdk directory.
2. Clone repository:
```
git clone https://github.com/ExaggeratedRumors/nyquist-bot.git
```
3. Create config.yaml file in data directory:
```
src/main/resources/data/config.yaml
```
4. Fill config.yaml file with configuration content:
```yaml
server:
  host: "irc.chat.twitch.tv"
  port: 6667
api:
  twitch_nickname: YOUR_ACCOUNT_NICKNAME
  twitch_client_id: YOUR_CLIENT_ID
  twitch_client_secret: YOUR_CLIENT_SECRET
  oauth_password: YOUR_OAUTH_PASSWORD
  access_token: YOUR_ACCESS_TOKEN
channels:
  - CHOSEN_CHANNEL_1
  - CHOSEN_CHANNEL_2
prefix: "!"
```
5. Open application root directory and put command:
```
./gradlew run
```