package vicarious;

import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;

class MyServletModule extends ServletModule {
  @Override
  protected void configureServlets() {
    serve("/random").with(VicariousAppEngine.class);
    bind(VicariousAppEngine.class).in(Scopes.SINGLETON);
  }
}
