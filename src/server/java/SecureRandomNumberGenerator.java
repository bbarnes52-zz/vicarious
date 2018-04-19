package vicarious;

import com.google.inject.Inject;
import java.security.SecureRandom;

final class SecureRandomNumberGenerator implements RandomNumberGenerator<Integer> {

  @Inject
  SecureRandomNumberGenerator() {}

  @Inject SecureRandom secureRandom;

  @Override
  public Integer get(int minValue, int maxValue) {
    return this.secureRandom.nextInt(maxValue - minValue) + minValue;
  }
}
