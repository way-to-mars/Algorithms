package lesson3

fun main() {

    (-16..16).forEach {
        println(it.mod(3))
    }

    val cmap = IntArray(26) { 0 }
    readln().forEach { cmap[it.code - 'a'.code]++ }
    readln().forEach { cmap[it.code - 'a'.code]-- }
    if (cmap.any { it != 0 }) println("NO")
    else println("YES")
}


/** C++ **
 * ******
 *
 * #include <iostream>
 * #include <vector>
 *
 *
 * using namespace std;
 *
 * int main()
 * {
 * 	std::string word1;
 * 	std::string word2;
 *
 * 	cin >> word1;
 * 	cin >> word2;
 *
 * 	if (word1.size() != word2.size()) {
 * 		cout << "NO";
 * 		return 0;
 * 	}
 *
 * 	int array[26] = { 0 };
 * 	for (int i = word1.size(); i >= 0; i--) {
 * 		array[word1[i] - 'a']++;
 * 		array[word2[i] - 'a']--;
 * 	}
 *
 * 	for (int i = 0; i < 26; i++)
 * 		if (array[i] != 0) {
 * 			cout << "NO";
 * 			return 0;
 * 		}
 *
 * 	cout << "YES";
 *
 * 	return 0;
 * }
 */