package vicarious;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import java.security.SecureRandom;

public final class AppModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(new TypeLiteral<RandomNumberGenerator<Integer>>() {})
        .to(SecureRandomNumberGenerator.class)
        .in(Singleton.class);
  }

  @Provides
  @Singleton
  SecureRandom provideSecureRandom() {
    // SecureRandom will seed itself.
    return new SecureRandom();
  }
}
