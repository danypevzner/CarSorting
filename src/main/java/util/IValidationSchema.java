package util;

import java.util.List;
import java.util.Optional;

public interface IValidationSchema<Field, Model> {
    Optional<String> validateField(Field field, Object value);
    Optional<List<String>> validate(Model model);
}
