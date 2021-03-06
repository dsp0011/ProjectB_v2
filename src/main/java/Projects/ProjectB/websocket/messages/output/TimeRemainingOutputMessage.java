package Projects.ProjectB.websocket.messages.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeRemainingOutputMessage extends OutputMessage {

    private String timeRemaining;
}
