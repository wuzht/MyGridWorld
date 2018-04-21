# Test Report

> Test Report for Jumper.java

## testJumper

* `Jumper`是默认构造函数，默认颜色是红色，测试创建出来的Jumper是否是红色的

* 测试方案：创建一个jumper

* 预期结果：该jumper的颜色是红色的

* 测试代码：

  ```java
  @Test
  public void testJumper() {
      Jumper jumper = new Jumper();
      assertEquals(Color.RED, jumper.getColor());
  }
  ```

  ​



## testJumperColor

* 构造函数`JumperColor`可以创建指定颜色的Jumper，测试能否设置正确的颜色

* 测试方案：

  * 创建一个黄色的jumper1
  * 创建一个蓝色的jumper2

* 预期结果：

  * jumper1的颜色是黄色的
  * jumper2的颜色是蓝色的

* 测试代码：

  ```java
  @Test
  public void testJumperColor() {
      Jumper jumper1 = new Jumper(Color.YELLOW);
      assertEquals(Color.YELLOW, jumper1.getColor());
      Jumper jumper2 = new Jumper(Color.BLUE);
      assertEquals(Color.BLUE, jumper2.getColor());
  }
  ```




## testTurn

- `turn`方法会使Jumper在原地向右转45度

- 测试方案：

  创建一个jumper(初始方向是NORTH)

  - 第一次调用turn
  - 第二次调用turn

- 预期结果：

  - jumper的方向为NORTHEAST
  - jumper的方向为EAST

- 测试代码：

  ```java
  @Test
  public void testTurn() {
      ActorWorld world = new ActorWorld();
      Jumper jumper = new Jumper();
      world.add(new Location(7, 7), jumper);
      jumper.turn();
      assertEquals(Location.NORTHEAST, jumper.getDirection());
      jumper.turn();
      assertEquals(Location.EAST, jumper.getDirection());
  }
  ```




## testJump

- `jump`方法会使Jumper往当前方向移动2格，若目的位置在grid外，则将自己删除

- 测试方案：

  创建一个jumper(初始方向是NORTH, 位置为(3, 7))

  - 第一次调用jump
  - 第二次调用jump

- 预期结果：

  - jumper的位置为(1, 7)
  - jumper的位置为null

- 测试代码：

  ```java
  @Test
  public void testJump() {
      ActorWorld world = new ActorWorld();
      Jumper jumper = new Jumper();
      world.add(new Location(3, 7), jumper);
      jumper.jump();
      assertEquals(new Location(1, 7), jumper.getLocation());
      // The target location is out of the grid, so jumper remove itself from the grid
      jumper.jump();
      Assert.assertTrue(jumper.getLocation() == null);
  }
  ```




## testCanJump

- `canJump`方法判断Jumper是否可以jump，若可以返回true，否则返回false

  - 若当前的grid为null，返回false
  - 若当前方向的第1个格子在grid外，返回false
  - 若当前方向的第1个格子不是null，且既不是Flower也不是Rock，返回false
  - 若当前方向的第2个格子在grid外，返回false
  - 若当前方向的第2个格子是null或者是Flower，返回true；否则返回false

- 测试方案：

  创建一个ActorWorld, 一个jumper(初始方向是NORTH, 位置为(0, 0))

  - 调用canJump
  - 将jumper移到(0, 1)，调用canJump
  - 将jumper移到(3, 3)，调用canJump
  - 在(2, 3)添加一个Rock，调用canJump
  - 在(1, 3)添加一个Rock，调用canJump
  - 将jumper移到(9, 9)，在(8, 9)添加一个flower，调用canJump
  - 在(7, 9)添加一个Flower，调用canJump
  - 将jumper移到(5, 9)，在(4, 9)添加一个Actor，调用canJump

- 预期结果：

  - 返回false，因为当前方向的第1个格子在grid外
  - 返回false，因为当前方向的第2个格子在grid外
  - 返回true
  - 返回true，因为当前方向的第1个格子是Rock，第2个格子为null
  - 返回false，因为当前方向的第2个格子是Rock
  - 返回true，因为当前方向的第1个格子是Flower，第2个格子为null
  - 返回true，因为当前方向的第1个格子是Flower，第2个格子为Flower
  - 返回false，因为当前方向的第1个格子是Actor

- 测试代码：

  ```java
  @Test
  public void testCanJump() {
      ActorWorld world = new ActorWorld();
      Jumper jumper = new Jumper();
      // False. Facing to the edge of the grid
      world.add(new Location(0, 0), jumper);
      Assert.assertFalse(jumper.canJump());
      // False. The target location is out of the grid
      jumper.moveTo(new Location(0, 1));
      Assert.assertFalse(jumper.canJump());
      // True.
      jumper.moveTo(new Location(3, 3));
      Assert.assertTrue(jumper.canJump());
      // True. Facing to a rock, and nothing in the target location
      world.add(new Location(2, 3), new Rock());
      Assert.assertTrue(jumper.canJump());
      // False. There is a rock in the target location
      world.add(new Location(1, 3), new Rock());
      Assert.assertFalse(jumper.canJump());
      // True. Facing to a flower, and nothing in the target location
      jumper.moveTo(new Location(9, 9));
      world.add(new Location(8, 9), new Flower());
      Assert.assertTrue(jumper.canJump());
      // True. There is a flower in the target location
      world.add(new Location(7, 9), new Flower());
      Assert.assertTrue(jumper.canJump());
      // False. There is an actor in front of it
      jumper.moveTo(new Location(5, 9));
      world.add(new Location(4, 9), new Actor());
      Assert.assertFalse(jumper.canJump());	
  }
  ```




## testAct

- `act`方法：若jumper.canJump() == true，则调用`jump`方法，否则调用`turn`方法

- 测试方案：

  创建一个jumper(初始方向是NORTH, 位置为(5, 5))，一个Rock(位置为(4, 5))

  - 调用act
  - 在(1, 5)添加一个Rock

- 预期结果：

  - jumper的位置为(3, 5)
  - jumper的位置为(3, 5)，且方向为NORTHEAST(向右转了45度)

- 测试代码：

  ```java
  @Test
  public void testAct() {
      ActorWorld world = new ActorWorld();
      Jumper jumper = new Jumper();
      world.add(new Location(5, 5), jumper);
      world.add(new Location(4, 5), new Rock());
      jumper.act();
      assertEquals(new Location(3, 5), jumper.getLocation());
      world.add(new Location(1, 5), new Rock());
      jumper.act();
      assertEquals(new Location(3, 5), jumper.getLocation());
      assertEquals(Location.NORTHEAST, jumper.getDirection());
  }
  ```

