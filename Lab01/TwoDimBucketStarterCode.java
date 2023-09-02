//Yifan Wan
//9/1/2023
public class TwoDimBucketStarterCode {

    public static void main(String[] args) {
        int[] heights = {1, 3, 4};
        System.out.println("Should be 3: " + findMaxArea(heights));
        
        int[] moreHeights = {3, 5, 4, 8, 7};
        System.out.println("Should be 15: " + findMaxArea(moreHeights));
    }

    /**
     * Finds the max 2D "water" area between two lines in the input array.
     *
     * @param height Array of line heights.
     * @return Max water area.
     */
    public static int findMaxArea(int[] height) {
        int maxArea = 0;  // Start with zero

        // Two pointers, one at the start and one at the end
        int left = 0;
        int right = height.length - 1;

        while (left < right) {
            // Find the shorter line
            int minHeight = Math.min(height[left], height[right]);

            // Update max area if needed
            maxArea = Math.max(maxArea, minHeight * (right - left));

            // Move the pointer at the shorter line inward
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return maxArea;
    }
}
