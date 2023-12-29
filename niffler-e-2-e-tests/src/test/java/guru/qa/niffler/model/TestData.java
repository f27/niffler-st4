package guru.qa.niffler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record TestData(
    @JsonIgnore String password
) {
}
