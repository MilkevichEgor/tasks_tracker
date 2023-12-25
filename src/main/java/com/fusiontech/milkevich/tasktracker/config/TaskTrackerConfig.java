package com.fusiontech.milkevich.tasktracker.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.instance.impl.HazelcastInstanceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * General application configuration.
 */
@Configuration
public class TaskTrackerConfig {

  /**
   * Hazelcast config.
   */
  @Bean
  public Config hazelcastConfig() {
    Config config = new Config();
    MapConfig mapConfig = new MapConfig("myMap");
    config.addMapConfig(mapConfig);
    config.getNetworkConfig().setPort(5701);
    MapConfig noBackupsMap = new MapConfig("dont-backup").setBackupCount(0);
    noBackupsMap.setTimeToLiveSeconds(5000);
    config.addMapConfig(noBackupsMap);
    return config;
  }

  @Bean
  public HazelcastInstance hazelcastInstance() {
    return HazelcastInstanceFactory.newHazelcastInstance(hazelcastConfig());
  }

}

