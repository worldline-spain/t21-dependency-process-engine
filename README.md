# T21 Dependency Process Engine

T21 Dependency Process Engine is a [command design pattern](https://en.wikipedia.org/wiki/Command_pattern) implementation with dependencies

[![](https://jitpack.io/v/worldline-spain/t21-dependency-process-engine.svg)](https://jitpack.io/#worldline-spain/t21-dependency-process-engine)

## Installation

- Add these dependencies to your project:
```bash
implementation 'com.github.worldline-spain:t21-dependency-process-engine:1.0.1'
```
- Add this to your root build.gradle
```bash
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```
## Usage

Each task needs to override the ```BaseCommand``` class like this:
```java
public class SampleTask extends BaseCommand<SampleReceiver, Result> {

    public static final String SAMPLE_TASK_ID = "task_id";

    public static final String SAMPLE_TASK_FINISHED = "sample_task_finished";

    public SampleTask(String... processSpecificRestrictions) {
        super(SAMPLE_TASK_ID, processSpecificRestrictions);
        updatingStates.add(SAMPLE_TASK_FINISHED);
    }

    @Override
    public void execute() {
        super.execute();
        //Your code here
        if (success) {
            handleSuccess(response);
        } else {
            handleError();
        }
    }

    @Override
    protected void handleSuccess(Result response) {
        updatedStates.add(SAMPLE_TASK_FINISHED);
        receiver.setResult(response);
        listener.commandFinished(this);
    }

    @Override
    protected void handleError() {
        errorStates.add(SAMPLE_TASK_FINISHED);
        listener.commandFinished(this);
    }
}
```
In the constructor you need to define the ID, and the state that will be updated by this command. This state will be later used to define the required states of the whole process.

In the execute method you need to call the ```super.execute()``` first to clean the state from the previous execution. Then you implement your logic. And finally you need to call the ```handleSuccess()``` and ```handleError()``` methods, which in turn will update the state, update the receiver if necessary, and call the listener.

The ```Receiver``` class is used for passing values between the tasks, and its type needs to be defined in each Command as well as the Invoker. It can be a simple String, or it can be a complex data structure that will contain all the necessary data for executing the different commands and saving their results.

Then you'll need to override the ```Invoker``` class to join all the commands together like this:
```java
public class SampleInvoker extends Invoker<SampleReceiver> {

    public static final String SAMPLE_INVOKER = "sampleInvoker";

    public T21SampleInvoker(SampleReceiver receiver,
            ProcessFinishedInterface<SampleReceiver> listener) {
        super(SAMPLE_INVOKER, receiver, listener,
                SAMPLE_TASK_FINISHED,
                ANOTHER_TASK_FINISHED);
        initialize();
    }

    private void initialize() {
        SampleTask sampleTask = new SampleTask();
        takeCommand(sampleTask);

        AnotherTask anotherTask = new AnotherTask();
        takeCommand(anotherTask);

        ...
    }
}

```

Finally, from your application code, you need to start the process like this:
```java
SampleInvoker sampleInvoker = new SampleInvoker(receiver, listener);
sampleInvoker.startProcess();
```
When you start the invoker, it will go through the tasks until it finds one it can execute. After it gets executed, the states are updated, and the process starts looking for the next task. When all the tasks are executed, and if all the required states (defined in the Invoker constructor) are fulfilled, the process is considered as successful. Otherwise, it's a failure. Furthermore, if any of the tasks finishes with an error state, the process also finishes and the remaining tasks are skipped.

Finally, in the listener you'll get the result:
```java
@Override
public void processFinished(int statusCode, Invoker<SampleReceiver> invoker) {
    if (statusCode == ProcessFinishedInterface.SUCCESS) {
        //SUCCESS ;)
    } else {
        //Failure :(
    }
}
```
For a more detailed example, please take a look at the sample project.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
This project is licensed under the Apache License - see the [LICENSE](LICENSE) file for more details

## Acknowledgments

* [Sergi Barea](https://github.com/sergibc) for all the help, motivation and enormous support
* [Bernat Borrás](https://github.com/alorma) for inspiring us with his [TimelineView](https://github.com/alorma/TimelineView) which was used in the sample app
