public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> result = new ArrayDeque();
        for (int i = 0; i < word.length(); i++) {
            result.addLast(word.substring(i, i+1).charAt(0));
        }
        return result;
    }

    public boolean isPalindrome(String word) {
        if (word.length() == 1 || word.length() == 0) {
            return true;
        }
        Deque<Character> d = wordToDeque(word);
        return (d.removeFirst() == d.removeLast() && isPalindrome(word.substring(1, word.length() - 1)));
        /*
        for (int i = 0; i < word.length() / 2; i++) {
            if (d.removeFirst() != d.removeLast()) {
                return false;
            }
        }
        return true;

         */
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() == 1 || word.length() == 0) {
            return true;
        }
        Deque<Character> d = wordToDeque(word);
        return (cc.equalChars(d.removeFirst(), d.removeLast()) && isPalindrome(word.substring(1, word.length() - 1), cc));
    }
}
