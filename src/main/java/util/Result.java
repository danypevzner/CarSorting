package util;

import java.util.List;

public sealed interface Result<Value> {
  public record Ok<Value>(Value value) implements Result<Value> {}
  public record Failure<Value>(List<String> errors) implements Result<Value> {}

  static <Value> Result<Value> ok(Value value) {
    return new Ok<>(value);
  }

  static <Value> Result<Value> failure(List<String> errors) {
    return new Failure<>(errors);
  }
}
