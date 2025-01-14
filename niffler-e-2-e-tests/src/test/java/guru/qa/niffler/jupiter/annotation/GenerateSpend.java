package guru.qa.niffler.jupiter.annotation;

import guru.qa.niffler.jupiter.extension.GenerateSpendExtension;
import guru.qa.niffler.jupiter.extension.SpendResolverExtension;
import guru.qa.niffler.model.CurrencyValues;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith({GenerateSpendExtension.class, SpendResolverExtension.class})
public @interface GenerateSpend {

  String description();

  double amount();

  CurrencyValues currency();
}
