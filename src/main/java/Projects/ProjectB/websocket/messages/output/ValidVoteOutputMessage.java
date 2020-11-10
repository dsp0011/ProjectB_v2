package Projects.ProjectB.websocket.messages.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidVoteOutputMessage extends OutputMessage {

    private long pollId;
    private int votesForAlternative1;
    private int votesForAlternative2;
}
