
package co.tyec.selenium.extjswebdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TreeNode extends ExtJSComponent
{

    final String getUIfunction = ".getUI()";

    /**
     * Field nodeExpression.
     */
    private String nodeExpression = "";

    /**
     * Field nodeUiExpression.
     */
    private String nodeUiExpression = "";

    /**
     * Field treeExpression.
     */
    private String treeExpression = "";

    public TreeNode(WebDriver driver, By by)
    {
        super(driver, by);
        treeExpression = getExpression();
    }

    /**
     * collapse the Selected node
     */
    public void collapseNode()
    {
        nodeExpression = ".getSelectionModel().getSelectedNode()";
        execScriptClean(String.format("%s.collapse()", nodeExpression));
    }

    /**
     * collapse the root Node
     */
    public void collapseRootNode()
    {
        nodeExpression = ".getSelectionModel().getSelectedNode()";
        execScriptClean(String.format("%s.collapse()", getRootNode()));
    }

    /**
     * Expand this node. the node must be selected;
     */
    public void expand()
    {
        getSelectedNode();
        execScriptClean(String.format("%s%s.expand()", treeExpression, nodeExpression));
    }

    /**
     * expend the Selected node
     */
    public void expentNode()
    {
        nodeExpression = ".getSelectionModel().getSelectedNode()";
        execScriptClean(String.format("%s.expand()", nodeExpression));
    }

    /**
     * expend the root Node
     */
    public void expentRootNode()
    {
        nodeExpression = ".getSelectionModel().getSelectedNode()";
        execScriptClean(String.format("%s.expand()", getRootNode()));
    }

    /**
     * Trying to search in tree and find a child with given value if found the node will be select
     * 
     * @param val
     * @return - selected node (if found one)
     */
    public TreeNode findChild(final String val)
    {
        if (nodeExpression.isEmpty() || nodeExpression.length() == 0)
        {
            getRootNode();
        }
        execScriptOnExtJsCmp(nodeExpression + ".findChild( 'name' ,'" + val + "' , true ).select()");
        return this;
    }

    /**
     * find specific node in tree by given attribute and SELECT him
     * 
     * @param attribute
     * @param value
     * @return
     */
    public TreeNode findChild(final String attribute, final String value)
    {
        execScriptClean(String.format("%s.getRootNode().findChild('%s','%s',true).select()", treeExpression, attribute, value));
        return this;
    }

    /**
     * Method findNodeGridChild.
     * 
     * @param val
     *            String
     * @return TreeNode
     */
    public TreeNode findNodeGridChild(final String val)
    {
        if (nodeExpression.isEmpty() || nodeExpression.length() == 0)
        {
            getRootNode();
        }
        execScriptClean(treeExpression + nodeExpression + ".findChild( 'name' ," + "\"<span style='color:black'>" + val
                        + "</span>\" , true ).select()");
        return this;
    }

    /**
     * Method getCell.
     * 
     * @param index
     *            Integer
     * @return String
     */
    public String getCell(final Integer index)
    {
        if (nodeUiExpression.isEmpty())
        {
            getNodeUI();
        }

        return ((String) execScriptClean(treeExpression + nodeUiExpression + ".childNodes[" + index + "].firstChild.innerHTML"))
                        .replaceAll("\\<.*?>", "");
    }

    /**
     * return child count of selected node
     * 
     * @return - the count of node child
     */
    public int getLength()
    {
        return Integer.parseInt((String) execScriptClean(String.format("%s%s.childNodes.length", treeExpression, nodeExpression)));

    }

    /**
     * return Node attribute
     * 
     * @param att
     * @return String
     */
    public String getNodeAtt(final String att)
    {
        return execScriptOnExtJsCmpReturnString(nodeExpression + ".attributes." + att);
    }

    /**
     * return node UI of the selected node
     * 
     * @return String
     */
    public String getNodeUI()
    {
        nodeUiExpression = nodeExpression + ".ui.getEl().firstChild";
        return (String) execScriptClean(treeExpression + nodeExpression + ".ui.getEl().firstChild");
    }

    /**
     * return the root node of given Tree
     * 
     * @return TreeNode
     */
    public TreeNode getRootNode()
    {
        nodeExpression = ".getRootNode()";
        execScriptClean(String.format("%s.%s", treeExpression, nodeExpression));
        return this;

    }

    /**
     * return the Selected node
     * 
     * @return TreeNode
     */

    public TreeNode getSelectedNode()
    {
        nodeExpression = ".getSelectionModel().getSelectedNode()";
        execScriptClean(treeExpression + nodeExpression);
        return this;
    }

    /**
     * return the value by given attribute (the node should be selected)
     * 
     * @param att
     * @return String
     */
    public String getSelectedNodeAtt(final String att)
    {
        return execScriptOnExtJsCmpReturnString("return extCmp.getSelectionModel().getSelectedNode().attributes." + att);

    }

    /**
     * check if the selected node has chides
     * 
     * @return boolean
     */
    public boolean hasChildNodes()
    {
        return execScriptOnExtJsCmpReturnBoolean(String.format("%s.hasChildNodes() ", nodeExpression));
    }

    /**
     * @return
     */
    public Boolean isChecked()
    {
        return execScriptOnExtJsCmpReturnBoolean(String.format("%s.isChecked() ", treeExpression + getUIfunction));
    }

    /**
     * Returns true if the selected node is the first child of its parent
     * 
     * @return boolean
     */
    public boolean isFirst()
    {
        return execScriptOnExtJsCmpReturnBoolean(String.format("%s.isFirst() ", nodeExpression));
    }

    /**
     * Returns true if the selected node node is a leaf
     * 
     * @return boolean
     */
    public boolean isLeaf()
    {
        return execScriptOnExtJsCmpReturnBoolean(String.format("%s.isLeaf() ", nodeExpression));
    }

    /**
     * Returns true if the selected node is Selected
     * 
     * @return boolean
     */
    public boolean isSelected()
    {
        return execScriptOnExtJsCmpReturnBoolean(String.format("%s.isSelected() ", nodeExpression));
    }

    /**
     * Method select.
     * 
     * @param id
     *            String
     * @return TreeNode
     */
    public TreeNode select(final String id)
    {
        execScriptClean(".getSelectionModel().select(" //
                        + getExpression() + ".nodeHash['" + id + "']" + //
                        ")");

        return getSelectedNode();
    }

    /**
     * Sets the checked status of the tree node to the passed value . The node must be selected
     * 
     * @param check
     */
    public void toggleCheck(final Boolean check)
    {
        execScriptClean(String.format("%s.getSelectionModel().getSelectedNode().getUI().toggleCheck(%s)", treeExpression, check));
    }

    /**
     * try to select an node and Sets the checked status of the tree node to the passed value .
     * 
     * @param attribute
     * @param value
     * @param check
     */
    public void toggleCheck(final String attribute, final String value, final Boolean check)
    {
        findChild(attribute, value);
        getSelectedNode();
        execScriptClean(String.format("%s%s%s.toggleCheck(%s)", treeExpression, nodeExpression, getUIfunction, check));
    }

}
