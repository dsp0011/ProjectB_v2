package Projects.ProjectB.websocket.messages.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InputMessage {

    private long pollId;
    private int votesForAlternative1;
    private int votesForAlternative2;
}
