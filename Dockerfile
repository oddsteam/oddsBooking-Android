FROM androidsdk/android-31
#RUN  apt-get update && \
#     apt-get install -y sudo && \
#     apt-get install -y ruby2.7.6 ruby-dev  && \
#     sudo gem install fastlane

RUN apt-get update && \
    apt-get install --no-install-recommends -y --allow-unauthenticated build-essential git ruby-full && \
    gem install rake && \
    gem install fastlane && \
    gem install bundler && \
    bundle update fastlane && \
    fastlane update_plugins && \
    # Clean up
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* && \
    apt-get autoremove -y && \
    apt-get clean

