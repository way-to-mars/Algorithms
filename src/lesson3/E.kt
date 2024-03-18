package lesson3

/**** C++ ****
 * *********
 *
 * #include <iostream>
 * #include <map>
 *
 * using namespace std;
 *
 * int main()
 * {
 * 	int n;
 * 	std::map<unsigned int, int8_t> total;  // sorted by keys
 * 	cin >> n;
 * 	for (int i = 0; i < n; ++i) {
 * 		unsigned int v;
 * 		cin >> v;
 * 		total[v] = 11;	// at least once in 1st
 * 	}
 *
 * 	cin >> n;
 * 	for (int i = 0; i < n; ++i) {
 * 		unsigned int v;
 * 		cin >> v;
 * 		if (total.contains(v)) {
 * 			if (total[v] == 11)
 * 				total[v] = 22;	// both in 1st and 2nd
 * 		}
 * 		else
 * 			total[v] = 21;		// only in 2nd
 * 	}
 *
 * 	cin >> n;
 * 	for (int i = 0; i < n; ++i) {
 * 		unsigned int v;
 * 		cin >> v;
 * 		if (total.contains(v)) {
 * 			if (total[v] / 10 < 3)
 * 				total[v] = 32;  // in 3rd and (1st and/or 2nd)
 * 		}
 * 		else
 * 			total[v] = 31;		// only in 3rd
 * 	}
 *
 * 	bool first = true;
 * 	for (const auto& [key, value] : total) {
 * 		if (value % 10 == 2)	// lowest digit 2 stands for "at least in two lists"
 * 		{
 * 			if (!first) {
 * 				cout << " " << key;
 * 			}
 * 			else {
 * 				first = false;
 * 				cout << key;
 * 			}
 * 		}
 * 	}
 *
 * 	return 0;
 * }
 **
 */

