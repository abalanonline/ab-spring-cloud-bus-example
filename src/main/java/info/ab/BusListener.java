/*
 * Copyright 2020 Aleksei Balan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package info.ab;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.bus.event.AckRemoteApplicationEvent;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.cloud.bus.event.UnknownRemoteApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BusListener implements ApplicationListener<RemoteApplicationEvent> {
  @Override
  public void onApplicationEvent(RemoteApplicationEvent event) {
    if (event instanceof AckRemoteApplicationEvent) return;
    if (event instanceof UnknownRemoteApplicationEvent) {
      log.info("RemoteEvent-" + ((UnknownRemoteApplicationEvent) event).getTypeInfo());
    } else {
      log.info(event.getClass().getName());
    }
  }
}
