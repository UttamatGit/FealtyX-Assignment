package org.fealtyx.sms_ai.model;

public class OllamaRequest {
    private final String model;
    private final String prompt;
    private final boolean stream;

    public OllamaRequest(String model, String prompt, boolean stream) {
        this.model = model;
        this.prompt = prompt;
        this.stream = stream;
    }

    public String getModel() {
        return model;
    }

    public String getPrompt() {
        return prompt;
    }

    public boolean isStream() {
        return stream;
    }

    @Override
    public String toString() {
        return "OllamaRequest{" +
                "model='" + model + '\'' +
                ", prompt='" + prompt + '\'' +
                ", stream=" + stream +
                '}';
    }
}