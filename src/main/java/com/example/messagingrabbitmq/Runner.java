package com.example.messagingrabbitmq;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class Runner implements CommandLineRunner {

	private final RabbitTemplate rabbitTemplate;
	private final Receiver receiver;

	public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
		this.receiver = receiver;
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Sending message...");
		//rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
		rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.fanoutExchangeName, "","Hello from RabbitMQ!");

		receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);

		Scanner in = new Scanner(System.in);
		while(true)
		{
			String msg = in.nextLine();
			if(msg == "exit")
			{
				break;
			}
			//rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.topicExchangeName, "foo.bar.baz", msg);
			rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.fanoutExchangeName, "", msg);

		}

	}

}
