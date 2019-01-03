package com.fabriceguillaume;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lexruntime.AmazonLexRuntime;
import com.amazonaws.services.lexruntime.AmazonLexRuntimeClientBuilder;
import com.amazonaws.services.lexruntime.model.PostTextRequest;
import com.amazonaws.services.lexruntime.model.PostTextResult;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("    *** Lex Bot POC ***     ");
        System.out.println("----------------------------");
        System.out.println("          OPTIONS           ");
        System.out.println("1. Automated 2. Interactive ");

        try {
            AmazonLexRuntime lexClient =
                    AmazonLexRuntimeClientBuilder.standard()
                            .withRegion(Regions.US_EAST_1)
                            .build();

            PostTextRequest textRequest = new PostTextRequest();
            textRequest.setBotAlias("testbotforrequest");
            textRequest.setBotName("TestBotForRequest");
            textRequest.setUserId("testuser");

            Scanner scanner = new Scanner(System.in);

            while(true) {
                System.out.println("Please enter an Option: ");
                try {
                    int userOption = scanner.nextInt();
                    if (userOption == 1) {
                        // Static list of strings to try different responses from Bot
                        List<String> listRequests = Arrays.asList("cfweqfr", "I need some apple", "I need 2 apple");

                        for (int i = 0; i < listRequests.size(); i++) {
                            System.out.println("\nRequest [" + i + "]: '" + listRequests.get(i) + "'");
                            textRequest.setInputText(listRequests.get(i));

                            PostTextResult textResult = lexClient.postText(textRequest);
                            System.out.println("Bot Response - getDialogState: " + textResult.getDialogState());
                            System.out.println("Bot Response - getMessage: '" + textResult.getMessage() + "'");
                            System.out.println("Bot Response - getIntentName: '" + textResult.getIntentName() + "'");
                            System.out.println("Bot Response - getSlots: '" + textResult.getSlots() + "'");
                        }
                        break;
                    } else if (userOption == 2) {
                        System.out.println("Enter your request: ");
                        scanner.nextLine();
                        while (true) {
                            String requestText = scanner.nextLine().trim();
                            if (requestText == null)
                                break;

                            textRequest.setInputText(requestText);
                            PostTextResult textResult = lexClient.postText(textRequest);

                            if (textResult.getDialogState().startsWith("Elicit"))
                                System.out.println(textResult.getMessage());
                            else if (textResult.getDialogState().equals("ReadyForFulfillment"))
                                System.out.println(String.format("%s: %s", textResult.getIntentName(), textResult.getSlots()));
                            else
                                System.out.println(textResult.toString());

                        }
                        System.out.println("Bye.");
                        break;
                    } else {
                        System.out.println("Invalid Option");
                        //break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Option -- You have not entered a number.");
                    scanner.nextLine();
                }
            }

        }
        catch(AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon Lex couldn't process
            e.printStackTrace();
        }
        catch(SdkClientException e) {
            // Amazon Lex couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon Lex.
            e.printStackTrace();
        }
    }
}
