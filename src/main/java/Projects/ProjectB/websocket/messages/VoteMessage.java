package Projects.ProjectB.websocket.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoteMessage {

    private long pollId;
    private int votesForAlternative1;
    private int votesForAlternative2;
}