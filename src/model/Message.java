package model;

/**
 * Created by thomasd on 12/12/16.
 */
public class Message {

    Agent emmeteur;
    Agent recepteur;
    /*
    Identifiant
    Performatif ( request -W on dit au autres de se bouger
    Action : move
    Position a libérer
     */

    public Message(Agent emmeteur, Agent recepteur) {
        this.emmeteur = emmeteur;
        this.recepteur = recepteur;
    }
}
