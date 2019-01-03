# Java POC to interact with Lex


## Dependencies:
### AWS Java SDK for Java 1.11
https://aws.amazon.com/sdk-for-java/
Dev documentation:
https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/welcome.html

### AWS Java SDK for AWS Lex
https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-lex


## Testing:
### Via Java console app
Just run the main()

### or Via CLI
https://docs.aws.amazon.com/lex/latest/dg/gs-create-test-text.html
```$ aws lex-runtime post-text  --region us-east-1  --bot-name TestBotForRequest  --bot-alias "\$LATEST" --user-id testuser --input-text "i would like to order 1 flower"```
