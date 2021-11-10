package es.ucm.vdm.logic.behaviours;

import es.ucm.vdm.logic.HintsManager;
import es.ucm.vdm.logic.engine.Behaviour;
import es.ucm.vdm.logic.hints.Hint;

public class TakeHintBehaviour implements Behaviour {
    private HintsManager _hintManager;

    public TakeHintBehaviour(HintsManager hm) {
        _hintManager = hm;
    }

    @Override
    public void onClick() {
        Hint h = _hintManager.getHint();
        h.setVisible(true);
        h.setPosition(0,0);
    }
}
