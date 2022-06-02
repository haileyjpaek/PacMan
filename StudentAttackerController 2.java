package edu.ufl.cise.cs1.controllers;
import game.controllers.AttackerController;
import game.models.*;
import java.awt.*;
import java.util.List;

public final class StudentAttackerController implements AttackerController
{
	public void init(Game game) { }

	public void shutdown(Game game) { }

	public int update(Game game,long timeDue)
	{
		int movements = -1;
		Attacker attacks = game.getAttacker();
		List<Defender> Ghost = game.getDefenders();
		List<Node> normalPill = game.getPillList();
		List<Node> strongerPill = game.getPowerPillList();
		Defender targetMovements = (Defender) (attacks.getTargetActor(Ghost, true));
		if(strongerPill.size() != 0) {
			game.getAttacker().addPathTo(game, Color.RED, strongerPill.get(0));
			Node nearestStrongPill = attacks.getTargetNode(strongerPill, true);
			movements = attacks.getNextDir(nearestStrongPill, true);
		}
		else if(normalPill.size() != 0) {
			Node nearNormalPill = attacks.getTargetNode(normalPill, true);
			movements = attacks.getNextDir(nearNormalPill, true);
		}
		if(targetMovements.isVulnerable() == true) {
			Node runAfter = targetMovements.getLocation();
			movements = attacks.getNextDir(runAfter, true);
		}
		else if(targetMovements.isVulnerable() == false && attacks.getPathTo(targetMovements.getLocation()).size() <= 4.269) {
		Node runAway = targetMovements.getLocation();
		movements = attacks.getNextDir(runAway, false);
	}
		return movements;
	}
}