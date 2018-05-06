package solution;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import jigsaw.Jigsaw;
import jigsaw.JigsawNode;


/**
 * 在此类中填充算法，完成重拼图游戏（N-数码问题）
 */
public class Solution extends Jigsaw {

    private int searchedNodesNum;           // 已访问节点数：用以记录所有访问过的节点的数量

    private Queue<JigsawNode> exploreList;  // 用以保存已发现但未访问的节点
    private Set<JigsawNode> visitedList;    // 用以保存已发现的节点
    /**
     * 拼图构造函数
     */
    public Solution() {
    }

    /**
     * 拼图构造函数
     * @param bNode - 初始状态节点
     * @param eNode - 目标状态节点
     */
    public Solution(JigsawNode bNode, JigsawNode eNode) {
        super(bNode, eNode);
    }

    /**
     *（实验一）广度优先搜索算法，求指定5*5拼图（24-数码问题）的最优解
     * 填充此函数，可在Solution类中添加其他函数，属性
     * @param bNode - 初始状态节点
     * @param eNode - 目标状态节点
     * @return 搜索成功时为true,失败为false
     */
    public boolean BFSearch(JigsawNode bNode, JigsawNode eNode) {
    	this.visitedList = new HashSet<>(1000);
        this.exploreList = new LinkedList<JigsawNode>();

        this.beginJNode = new JigsawNode(bNode);
        this.endJNode = new JigsawNode(eNode);
        this.currentJNode = null;

        // 访问节点数大于29000个则认为搜索失败
        final int MAX_NODE_NUM = 29000;
        final int DIRS = 4;

        // 重置求解标记
        this.searchedNodesNum = 0;
        //this.solutionPath = null;
        this.reset();

        // (1)将起始节点放入exploreList中
        this.visitedList.add(this.beginJNode);
        this.exploreList.add(this.beginJNode);

        // (2) 如果exploreList为空，或者访问节点数大于MAX_NODE_NUM个，则搜索失败，问题无解;否则循环直到求解成功
        while (this.searchedNodesNum < MAX_NODE_NUM && !this.exploreList.isEmpty()) {
            this.searchedNodesNum++;

            // (2-1)取出exploreList的第一个节点N，置为当前节点currentJNode
            //      若currentJNode为目标节点，则搜索成功，计算解路径，退出
            this.currentJNode = this.exploreList.poll();
            if (this.currentJNode.equals(eNode)) {
                this.getPath();
                break;
            }

            // 记录并显示搜索过程
            // System.out.println("Searching.....Number of searched nodes:" + searchedNodesNum +
            //     "    Est:" + this.currentJNode.getEstimatedValue() +
            //     "    Current state:" + this.currentJNode.toString());

            JigsawNode[] nextNodes = new JigsawNode[]{
                new JigsawNode(this.currentJNode), new JigsawNode(this.currentJNode),
                new JigsawNode(this.currentJNode), new JigsawNode(this.currentJNode)
            };

            // (2-2)寻找所有与currentJNode邻接且未曾被发现的节点，将它们按代价估值从小到大排序插入exploreList中
            //         并加入visitedList中，表示已发现
            for (int i = 0; i < DIRS; i++) {
                if (nextNodes[i].move(i) && !this.visitedList.contains(nextNodes[i])) {
                    //JigsawNode tempJNode = new JigsawNode(nextNodes[i]);
                    //this.estimateValue(tempJNode);
                    //nextNodes[i].setEstimatedValue(tempJNode.getEstimatedValue());
                    this.visitedList.add(nextNodes[i]);
                    this.exploreList.add(nextNodes[i]);
                }
            }
        }

        System.out.println("Jigsaw BFSearch Result:");
        System.out.println("Begin state:" + this.getBeginJNode().toString());
        System.out.println("End state:" + this.getEndJNode().toString());
        // System.out.println("Solution Path: ");
        // System.out.println(this.getSolutionPath());
        System.out.println("Total number of searched nodes:" + this.searchedNodesNum);
        System.out.println("Depth of the current node is:" + this.getCurrentJNode().getNodeDepth());
        return this.isCompleted();
    }


    /**
     *（Demo+实验二）计算并修改状态节点jNode的代价估计值:f(n)
     * 如 f(n) = s(n). s(n)代表后续节点不正确的数码个数
     * 此函数会改变该节点的estimatedValue属性值
     * 修改此函数，可在Solution类中添加其他函数，属性
     * @param jNode - 要计算代价估计值的节点
     */
    public void estimateValue(JigsawNode jNode) {
        int s = 0; // 后续节点不正确的数码个数
        // 横向后续节点不正确的数码个数
        int dimension = JigsawNode.getDimension();
        for (int index = 1; index < dimension * dimension; index++) {
        	// 遇到空白块不算
        	if (jNode.getNodesState()[index] == 0 || jNode.getNodesState()[index + 1] == 0) {
        		continue;
        	}
            if (jNode.getNodesState()[index] + 1 != jNode.getNodesState()[index + 1]) {
                s++;
            }
        }
        
        // 纵向后续节点不正确的数码个数
        for (int index = 1; index < dimension * dimension; index++) {
        	// 最后一行不算
        	if (index > dimension * dimension - dimension) {
        		continue;
        	}
        	// 遇到空白块不算
        	if (jNode.getNodesState()[index] == 0 || jNode.getNodesState()[index + 1] == 0) {
        		continue;
        	}
        	if (jNode.getNodesState()[index] + dimension != jNode.getNodesState()[index + dimension]) {
        		s++;
        	}
        }
        
        int d = 0; // 所有 放错位的数码与其正确位置的距离的平方 之和
        int m = 0; // 曼哈顿距离
        for (int i = 1; i <= dimension * dimension; i++) {
        	if (jNode.getNodesState()[i] == 0) {
        		continue;
        	}
        	// 与endState比较
        	for (int j = 1; j < dimension * dimension; j++) {
        		if (endJNode.getNodesState()[j] == jNode.getNodesState()[i]) {
        			int realRow = (j - 1) / dimension;
        			int realCol = (j - 1) % dimension;
        			int nowRow = (i - 1) / dimension;
        			int nowCol = (i - 1) % dimension;
        			d += (realRow - nowRow) * (realRow - nowRow) + (realCol - nowCol) * (realCol - nowCol);
        			m += Math.abs(realRow - nowRow) + Math.abs(realCol - nowCol);
        			break;
        		}
        	}
        }
        jNode.setEstimatedValue(8 * s + 7 * d + 4 * m);
    }
}
