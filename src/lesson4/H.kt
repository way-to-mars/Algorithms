package lesson4

fun main() {
    val n = readln().toInt()
    val parties = (1..n).map {
        val (votes, bribe) = readln().split(" ").map(String::toInt)
        Party(index = it, v = votes, p = bribe)
    }.sortedBy { it.v }

    val maxP = parties.maxOf { it.p }

    val suffix = LongArray(n + 1)
    suffix[n] = 0
    for (i in n - 1 downTo 0)
        suffix[i] = suffix[i + 1] + parties[i].v

    var minP = suffix[0] + maxP + 1
    var bestParty = -1

    for (i in parties.indices) {
        if (parties[i].p < 0 || parties[i].p >= minP) continue
        var l = parties[i].v.toLong()
        var r = suffix[0] + maxP + 1

        var res = -1L
        while (l <= r) {
            val mid = (l + r) / 2
            var index = binarySearch(mid.toInt(), n, parties)
            if (index < n && mid == parties[i].v.toLong()) index++
            val dBest = mid - parties[i].v.toLong()
            val dPref = suffix[index] - (n - index) * (mid - 1)
            if (index == n || dBest >= dPref) {
                r = mid - 1
                res = mid
            } else {
                l = mid + 1
            }
        }
        val p = res - parties[i].v.toLong() + parties[i].p.toLong()

        if (p < minP) {
            bestParty = i
            minP = p
        }
    }

    var vote = minP - parties[bestParty].p
    var index = binarySearch((parties[bestParty].v + vote).toInt(), n, parties)
    parties[bestParty].v += vote.toInt()

    for(i in index..<n){
        if (i != bestParty){
            val dv = parties[i].v - parties[bestParty].v + 1
            parties[i].v = parties[bestParty].v - 1
            vote -= dv
        }
    }

    index = n
    while (vote > 0) {
        index--
        if (index == bestParty) continue
        if (parties[index].v > vote) {
            parties[index].v -= vote.toInt()
            vote = 0
        } else {
            vote -= parties[index].v.toLong()
            parties[index].v = 0
        }
    }
    println(minP)
    println(parties[bestParty].index)
    parties.sortedBy { it.index }.joinToString(separator = " ") { it.v.toString() }.also(::println)
}

data class Party(var index: Int, var v: Int, var p: Int)

fun binarySearch(value: Int, n: Int, list: List<Party>): Int {
    var l = -1
    var r = n
    while (r - l > 1) {
        val mid = (l + r) / 2
        if (list[mid].v >= value)
            r = mid
        else
            l = mid
    }
    return r
}

/*


110
37 65
64 29
60 32
36 67
59 46
40 53
33 68
51 71
46 74
41 41
33 65
48 26
43 29
38 21
50 47
66 27
59 44
67 61
56 43
40 21
40 20
29 59
39 50
21 58
54 55
36 71
59 68
31 26
59 44
69 37
63 32
43 22
66 21
68 76
57 79
51 62
69 24
59 54
46 41
47 23
46 30
46 70
65 24
32 69
22 68
53 28
25 34
63 43
70 49
68 28
51 30
32 68
21 51
54 47
26 68
67 58
42 28
60 31
67 35
56 71
51 23
46 69
67 74
26 75
22 41
57 48
46 -1
27 27
38 55
47 60
49 36
43 30
37 30
27 80
41 50
50 49
20 30
25 39
70 23
66 60
37 36
61 48
43 48
39 75
53 23
46 40
67 20
45 62
55 43
61 37
34 26
40 36
31 78
68 31
37 65
56 35
46 38
38 36
53 72
20 28
27 49
58 76
56 58
65 58
65 35
46 37
34 21
31 20
58 50
41 30

 */