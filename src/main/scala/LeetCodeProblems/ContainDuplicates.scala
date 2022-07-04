package LeetCodeProblems

object ContainDuplicates extends App {
  /*
  * Given an integer array nums, return true if any value appears at least twice in the array, and return false if every element is distinct.
Example 1:

Input: nums = [1,2,3,1]
Output: true
Example 2:

Input: nums = [1,2,3,4]
Output: false
Example 3:

Input: nums = [1,1,1,3,3,4,3,2,4,2]
Output: true*/
  def containsDuplicate(nums: Array[Int]): Boolean = {
    nums.toSet.size != nums.length
  }
  @annotation.tailrec
  def containsDups[A](list: List[A], seen: Set[A] = Set[A]()): Boolean =
    list match {
      case x :: xs => if (seen.contains(x)) true else containsDups(xs, seen + x)
      case _ => false
    }
  containsDups(List(1,1,2,3))
  val result = containsDuplicate(Array(1,2,3,4))
   println(result)

}
