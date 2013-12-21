package gameController;

import gameGUI.MainGamePanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseListen extends MouseAdapter{
MainGamePanel mgp;
	public MouseListen(MainGamePanel mgp) {
		// TODO Auto-generated constructor stub
	this.mgp = mgp;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		mgp.setMouseEvent(e);
		mgp.checkButtonClicked();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		mgp.setMouseEvent(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		mgp.setMouseEvent(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mgp.setMouseEvent(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		mgp.setMouseEvent(e);
	}

}
