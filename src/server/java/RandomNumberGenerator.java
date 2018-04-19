package vicarious;

public interface RandomNumberGenerator<T> {

  /** Returns a random number between start and end. */
  T get(int minValue, int maxValue);
}
