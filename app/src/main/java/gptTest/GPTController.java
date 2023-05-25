package gptTest;

import io.github.flashvayne.chatgpt.dto.ChatRequest;
import io.github.flashvayne.chatgpt.dto.ChatResponse;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatboot")
public class GPTController implements InitializingBean {

    @Autowired
    private ChatgptService chatGptService;

    @Override
    public void afterPropertiesSet() {
        System.out.println("======== Starting Chat GPT Boot ==========");
    }

    @GetMapping("chat")
    public String chatWith(@RequestParam String message) {
        System.out.println(message);
        return chatGptService.sendMessage(message);
    }

    @GetMapping("/prompt")
    public ChatResponse prompt (@RequestParam String message) {
        //The maximum number of tokens to generate in the completion. The token count of your prompt plus max.tok
        Integer maxTokens = 300;
        // GPT-3 models can understand and generate natural Language. We offer four main models with different 1
        String model = "text-davinci-003";
        // What sampling temperature to use: Higher values means the model will take more risks. Try 0.9 for mor
        Double temperature = 0.5;
        //An alternative to sampling with temperature, called nucleus sampling, where the model considers the re
        Double topP = 1.0;
        ChatRequest chatRequest = new ChatRequest(model, message, maxTokens, temperature, topP);
        ChatResponse res = chatGptService.sendChatRequest(chatRequest);
        System.out.println("Response was:" + res.toString());
        return res;
    }
}
