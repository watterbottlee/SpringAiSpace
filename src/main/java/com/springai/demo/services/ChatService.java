package com.springai.demo.services;

import java.util.List;

public interface ChatService {

    String getResponse(String query);

    void saveData(List<String> data);
}
