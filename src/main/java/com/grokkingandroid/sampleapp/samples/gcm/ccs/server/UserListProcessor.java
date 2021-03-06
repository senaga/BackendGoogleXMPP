/*
 * Copyright 2014 Wolfram Rittmeyer.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.grokkingandroid.sampleapp.samples.gcm.ccs.server;

import java.util.Map;
import java.util.Set;

/**
 * Handles a user registration.
 */
public class UserListProcessor implements PayloadProcessor{

    private static final String MESSAGE_KEY = "SM";
    
    @Override
    public void handleMessage(CcsMessage msg) {
        PseudoDao dao = PseudoDao.getInstance();
        CcsClient client = CcsClient.getInstance();
        String msgId = dao.getUniqueMessageId();
        Map<String, String> payload = msg.getPayload();
        payload.put(msgId, msgId);
        
	payload.put(MESSAGE_KEY, "USERLIST");	
        
        Set<String> accounts = dao.getAccounts();
        String users = "";
        for(String account: accounts) {
            users = users + account + ":";
        }           
        
	payload.put("USERLIST", users);
        
        String jsonRequest = 
                CcsClient.createJsonMessage(
                        msg.getFrom(), 
                        msgId, 
                        payload, 
                        null, 
                        null, // TTL (null -> default-TTL) 
                        false);
        client.send(jsonRequest);
    }
}
