package org.atdl4j.parser.test;

import java.util.Iterator;

import org.atdl4j.atdl.xmlbeans.layout.StrategyPanelT;
import org.w3c.dom.Node;

/**
 * A StrategyPanel node can have ParameterT's and StrategyPanel as children.
 * Using the XmlBeans parser it's not possible to iterate over the elements
 * following the XML order. This iterator allows the user to do this.
 * 
 * <code>
 * Iterator<Object> it = new StrategyPanelIterator(panel);
 * while (it.hasNext()) {
 * 	Object obj = it.next();
 * 	if (obj instanceof ParameterT) {
 * 		// handle that
 * 	} else if (obj instanceof StrategyPanel) {
 * 		// handle that
 * 	}
 * }
 * </code>
 * 
 * @author tuler
 * 
 */
public class StrategyPanelIterator implements Iterator<Object> {

	private StrategyPanelT panel;

	private int i;

	private int paramIndex;

	private int panelIndex;

	public StrategyPanelIterator(StrategyPanelT panel) {
		this.panel = panel;
		this.i = 0;
		this.paramIndex = 0;
		this.panelIndex = 0;
	}

	@Override
	public boolean hasNext() {
		int length = panel.getDomNode().getChildNodes().getLength();
		return i < length;
	}

	/**
	 * Returns the current element. The user must check if it's an instanceof
	 * ParameterT or StrategyPanel.
	 */
	@Override
	public Object next() {
		Object ret = null;
		int length = panel.getDomNode().getChildNodes().getLength();
		while (i < length && ret == null) {
			// get i-child
			Node node = panel.getDomNode().getChildNodes().item(i);

			// se if it matches with current parameter node
			if (panel.getControlArray().length > paramIndex) {
				Node parameterNode = panel.getControlArray(paramIndex)
						.getDomNode();
				if (parameterNode != null && parameterNode.equals(node)) {
					// return it and goes to the next parameter
					ret = panel.getControlArray(paramIndex);
					paramIndex++;
				}
			}

			// se if it matches with current panel node
			if (panel.getStrategyPanelArray().length > panelIndex) {
				Node panelNode = panel.getStrategyPanelArray(panelIndex)
						.getDomNode();
				if (panelNode != null && panelNode.equals(node)) {
					// return it and goes to the next panel
					ret = panel.getStrategyPanelArray(panelIndex);
					panelIndex++;
				}
			}

			i++;
		}
		return ret;
	}

	@Override
	public void remove() {
		throw new RuntimeException("operation not supported");
	}

}
