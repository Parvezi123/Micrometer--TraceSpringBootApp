# Trace SpringBoot Application using MicroMeter

## Using Micrometer to trace your Spring Boot application

Adding Micrometer to your Spring Boot 3 application
The first step to instrumenting your application is to add Micrometer. Now it’s important to understand that Micrometer actually has two key aspects: tracing and metrics.

- Tracing — seeing WHAT happened
- Metrics — seeing HOW LONG it took to happen
  Sometimes people want one of the other. But for this article, we’ll do both. And to do that, you need to add the following to your build file:

## Installation

Along with SpringWeb and Lombok dependencies
```bash
        <dependency>
            <groupId>io.micrometer</groupId>
            <artifactId>micrometer-observation</artifactId>
        </dependency>
        <dependency>
            <groupId>io.micrometer</groupId>
            <artifactId>micrometer-tracing-bridge-brave</artifactId>
        </dependency>
        <dependency>
            <groupId>io.zipkin.reporter2</groupId>
            <artifactId>zipkin-reporter-brave</artifactId>
        </dependency>
```
- micrometer-observation — this pulls in the Micrometer code responsible for making Observations.
- micrometer-tracing-bridge-brave — this pulls in the Micrometer Tracing facade along with glue code to use Brave, the tracing solution from the Zipkin team.
- zipking-reporter-brave — a little more glue code to adapt Zipkin with Brave.

There’s a lot of terms thrown in there, so let’s discuss a little further. Micrometer has a concept called an Observation. This is when you essentially want to “see” what has happened during a key operation. This could be when a web request is made. Or when a database is accessed. The idea is that you A) start the Observation, B) do the action, and C) stop the Observation.

What if you wrap an Observation around a web method in your Spring MVC controller? Does this mean, that if inside the web controller, a Spring Data repository is invoked, we’d have to repeat the SAME PATTERN there too?

Yes…and no.

## Instrumenting code

Micrometer’s APIs are carefully designed such that when you start an Observation, the context of the scenario is stored and then passed along. For a a classic Spring MVC app, thread locals are used. This is done in a way such that every method in the call stack is able to grab the Observation and add to it. Essentially, more information can be gathered.

So this is the “yes” part of the answer. To trace every step in a call stack, you need to do this pattern in every method.

But the “no” part is that YOU DON’T HAVE to.

The Spring team has been working diligently at instrumenting all levels of the portfolio such that it’s already done. Building a Spring MVC controller? The DispatcherServlet and all its components will have already started an Observation before you web method is called.

Making a remote HTTP call to a 3rd party service? The Spring Framework team has instrumented RestTemplate and WebClient so the Observation pattern wraps around every remote call made. (And Micrometer is slick enough to pass along the tracing context over network boundaries!)

## Running your own copy of Zipkin

To make the magic happen, you need to run your own copy of Zipkin. Using Docker, this is a snap. Just execute the following command:

```bash
docker run -d -p 9411 openzipkin/zipkin:latest
```
#### OR

use docker-compose command to run docker-compose.yaml file

#### Once you execute this, you can then visit the it at localhost:9411

## Configuring your Spring Boot application for tracing
It doesn’t take much.

For starters, does your application include spring-boot-starter-actuator? The Actuator module is required for tracing to operate. If not, go update your build file. I’ll wait.

Now add the following attribute to your application.properties file:

#Trace every action
```bash
management.tracing.sampling.probability=1.0
```

By default, Micrometer only traces 10% or 0.1 of the time. This is to avoid taxing your system too much. (Actually a problem I’ve seen in my career is that tracing can get WAY out of control.)

We’re ONLY dialing up the levels to make it easier to see the tracing. For a real world, production system, you may want to back off to 0.1.

## Conclusion
With all this, you are able to start tracing your application and see what’s happening.

## 🚀 About Me
I'm a Software Developer Engineer in Siemens, Bengaluru...

## Follow Me
[Parvez@linked](https://www.linkedin.com/in/imparvez/) - [Parvez@Twitter](https://twitter.com/Parvez__AI) - [Parvez@Github](https://github.com/Parvezi123)