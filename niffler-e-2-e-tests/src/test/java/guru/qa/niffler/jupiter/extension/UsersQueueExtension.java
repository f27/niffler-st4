package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.TestData;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static guru.qa.niffler.jupiter.annotation.User.UserType.*;

@SuppressWarnings("unchecked")
public class UsersQueueExtension implements BeforeEachCallback, AfterTestExecutionCallback, ParameterResolver {

    public static final ExtensionContext.Namespace USERS_QUEUE_NAMESPACE
            = ExtensionContext.Namespace.create(UsersQueueExtension.class);

    private static final Map<User.UserType, Queue<UserJson>> USERS = new ConcurrentHashMap<>();

    static {
        Queue<UserJson> friendsQueue = new ConcurrentLinkedQueue<>();
        friendsQueue.add(user("dima", "12345"));
        friendsQueue.add(user("duck", "12345"));


        Queue<UserJson> invitationSentQueue = new ConcurrentLinkedQueue<>();
        invitationSentQueue.add(user("bee", "12345"));
        invitationSentQueue.add(user("dog", "12345"));

        Queue<UserJson> invitationReceivedQueue = new ConcurrentLinkedQueue<>();
        invitationReceivedQueue.add(user("barsik", "12345"));
        invitationReceivedQueue.add(user("fish", "12345"));

        USERS.put(WITH_FRIENDS, friendsQueue);
        USERS.put(INVITATION_SENT, invitationSentQueue);
        USERS.put(INVITATION_RECEIVED, invitationReceivedQueue);
    }

    private static UserJson user(String username, String password) {
        return new UserJson(
                null,
                username,
                null,
                null,
                CurrencyValues.RUB,
                null,
                null,
                new TestData(
                        password
                )
        );
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        List<Parameter> parameters = getAllParametersFromBeforeEachMethods(context);
        Collections.addAll(parameters, context.getRequiredTestMethod().getParameters());
        parameters = parameters.stream()
                .filter(parameter -> parameter.isAnnotationPresent(User.class))
                .filter(parameter -> parameter.getType().isAssignableFrom(UserJson.class))
                .toList();

        context.getStore(USERS_QUEUE_NAMESPACE)
                .getOrComputeIfAbsent(context.getUniqueId(), s -> new HashMap<User.UserType, UserJson>());

        for (Parameter parameter : parameters) {
            User annotation = parameter.getAnnotation(User.class);
            if (context.getStore(USERS_QUEUE_NAMESPACE)
                    .get(context.getUniqueId(), Map.class)
                    .get(annotation.value()) != null) {
                continue;
            }
            UserJson testCandidate = null;
            Queue<UserJson> queue = USERS.get(annotation.value());
            while (testCandidate == null) {
                testCandidate = queue.poll();
            }
            context.getStore(USERS_QUEUE_NAMESPACE)
                    .get(context.getUniqueId(), Map.class)
                    .put(annotation.value(), testCandidate);
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Map<User.UserType, UserJson> usersFromTest = (Map<User.UserType, UserJson>) context.getStore(USERS_QUEUE_NAMESPACE)
                .get(context.getUniqueId(), Map.class);
        for (User.UserType userType : usersFromTest.keySet()) {
            USERS.get(userType).add(usersFromTest.get(userType));
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter()
                .getType()
                .isAssignableFrom(UserJson.class) &&
                parameterContext.getParameter().isAnnotationPresent(User.class);
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        //noinspection OptionalGetWithoutIsPresent
        return (UserJson) extensionContext.getStore(USERS_QUEUE_NAMESPACE)
                .get(extensionContext.getUniqueId(), Map.class)
                .get(parameterContext.findAnnotation(User.class).get().value());
    }

    private List<Parameter> getAllParametersFromBeforeEachMethods(ExtensionContext context) {
        List<Parameter> parameters = new ArrayList<>();
        List<Method> allBeforeEachMethods = Arrays.stream(context.getRequiredTestClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(BeforeEach.class))
                .toList();
        for (Method beforeEachMethod : allBeforeEachMethods) {
            Collections.addAll(parameters, beforeEachMethod.getParameters());
        }
        return parameters;
    }
}
