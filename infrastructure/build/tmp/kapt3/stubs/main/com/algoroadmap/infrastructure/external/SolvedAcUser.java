package com.algoroadmap.infrastructure.external;

/**
 * solved.ac API 사용자 정보 응답
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b6\b\u0086\b\u0018\u00002\u00020\u0001B\u00bf\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\t\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u000f\u001a\u00020\t\u0012\u0006\u0010\u0010\u001a\u00020\t\u0012\u0006\u0010\u0011\u001a\u00020\t\u0012\u0006\u0010\u0012\u001a\u00020\t\u0012\u0006\u0010\u0013\u001a\u00020\t\u0012\u0006\u0010\u0014\u001a\u00020\t\u0012\u0006\u0010\u0015\u001a\u00020\t\u0012\u0006\u0010\u0016\u001a\u00020\t\u0012\u0006\u0010\u0017\u001a\u00020\t\u0012\u0006\u0010\u0018\u001a\u00020\t\u0012\u0006\u0010\u0019\u001a\u00020\u001a\u0012\u0006\u0010\u001b\u001a\u00020\u001a\u00a2\u0006\u0002\u0010\u001cJ\t\u00105\u001a\u00020\u0003H\u00c6\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\tH\u00c6\u0003J\t\u00108\u001a\u00020\tH\u00c6\u0003J\t\u00109\u001a\u00020\tH\u00c6\u0003J\t\u0010:\u001a\u00020\tH\u00c6\u0003J\t\u0010;\u001a\u00020\tH\u00c6\u0003J\t\u0010<\u001a\u00020\tH\u00c6\u0003J\t\u0010=\u001a\u00020\tH\u00c6\u0003J\t\u0010>\u001a\u00020\tH\u00c6\u0003J\t\u0010?\u001a\u00020\tH\u00c6\u0003J\u000b\u0010@\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010A\u001a\u00020\tH\u00c6\u0003J\t\u0010B\u001a\u00020\u001aH\u00c6\u0003J\t\u0010C\u001a\u00020\u001aH\u00c6\u0003J\u000b\u0010D\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010E\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010F\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010G\u001a\u00020\tH\u00c6\u0003J\t\u0010H\u001a\u00020\tH\u00c6\u0003J\t\u0010I\u001a\u00020\fH\u00c6\u0003J\t\u0010J\u001a\u00020\tH\u00c6\u0003J\u00ef\u0001\u0010K\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\t2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u000f\u001a\u00020\t2\b\b\u0002\u0010\u0010\u001a\u00020\t2\b\b\u0002\u0010\u0011\u001a\u00020\t2\b\b\u0002\u0010\u0012\u001a\u00020\t2\b\b\u0002\u0010\u0013\u001a\u00020\t2\b\b\u0002\u0010\u0014\u001a\u00020\t2\b\b\u0002\u0010\u0015\u001a\u00020\t2\b\b\u0002\u0010\u0016\u001a\u00020\t2\b\b\u0002\u0010\u0017\u001a\u00020\t2\b\b\u0002\u0010\u0018\u001a\u00020\t2\b\b\u0002\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010\u001b\u001a\u00020\u001aH\u00c6\u0001J\u0013\u0010L\u001a\u00020\u001a2\b\u0010M\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010N\u001a\u00020\tH\u00d6\u0001J\t\u0010O\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001eR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001eR\u0011\u0010\r\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001eR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001eR\u0011\u0010\u001b\u001a\u00020\u001a\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\'R\u0011\u0010\u0019\u001a\u00020\u001a\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\'R\u0011\u0010\u0017\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\"R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001eR\u0011\u0010\u0018\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\"R\u0011\u0010\u0012\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\"R\u0011\u0010\u0014\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\"R\u0011\u0010\u0013\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\"R\u0011\u0010\u0015\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010\"R\u0011\u0010\u0016\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\"R\u0011\u0010\u0010\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010\"R\u0011\u0010\u000f\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010\"R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010\"R\u0011\u0010\u0011\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010\"R\u0011\u0010\n\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010\"\u00a8\u0006P"}, d2 = {"Lcom/algoroadmap/infrastructure/external/SolvedAcUser;", "", "handle", "", "bio", "badgeId", "backgroundId", "profileImageUrl", "solvedCount", "", "voteCount", "exp", "", "class", "classDecoration", "rivalCount", "reverseRivalCount", "tier", "rating", "ratingByProblemsSum", "ratingByClass", "ratingBySolvedCount", "ratingByVoteCount", "maxStreak", "rank", "isRival", "", "isReverseRival", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIJILjava/lang/String;IIIIIIIIIIZZ)V", "getBackgroundId", "()Ljava/lang/String;", "getBadgeId", "getBio", "getClass", "()I", "getClassDecoration", "getExp", "()J", "getHandle", "()Z", "getMaxStreak", "getProfileImageUrl", "getRank", "getRating", "getRatingByClass", "getRatingByProblemsSum", "getRatingBySolvedCount", "getRatingByVoteCount", "getReverseRivalCount", "getRivalCount", "getSolvedCount", "getTier", "getVoteCount", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "infrastructure"})
public final class SolvedAcUser {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String handle = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String bio = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String badgeId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String backgroundId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String profileImageUrl = null;
    private final int solvedCount = 0;
    private final int voteCount = 0;
    private final long exp = 0L;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String classDecoration = null;
    private final int rivalCount = 0;
    private final int reverseRivalCount = 0;
    private final int tier = 0;
    private final int rating = 0;
    private final int ratingByProblemsSum = 0;
    private final int ratingByClass = 0;
    private final int ratingBySolvedCount = 0;
    private final int ratingByVoteCount = 0;
    private final int maxStreak = 0;
    private final int rank = 0;
    private final boolean isRival = false;
    private final boolean isReverseRival = false;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
        return null;
    }
    
    public final int component11() {
        return 0;
    }
    
    public final int component12() {
        return 0;
    }
    
    public final int component13() {
        return 0;
    }
    
    public final int component14() {
        return 0;
    }
    
    public final int component15() {
        return 0;
    }
    
    public final int component16() {
        return 0;
    }
    
    public final int component17() {
        return 0;
    }
    
    public final int component18() {
        return 0;
    }
    
    public final int component19() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    public final int component20() {
        return 0;
    }
    
    public final boolean component21() {
        return false;
    }
    
    public final boolean component22() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final long component8() {
        return 0L;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.algoroadmap.infrastructure.external.SolvedAcUser copy(@org.jetbrains.annotations.NotNull()
    java.lang.String handle, @org.jetbrains.annotations.Nullable()
    java.lang.String bio, @org.jetbrains.annotations.Nullable()
    java.lang.String badgeId, @org.jetbrains.annotations.Nullable()
    java.lang.String backgroundId, @org.jetbrains.annotations.Nullable()
    java.lang.String profileImageUrl, int solvedCount, int voteCount, long exp, int p8_47371452, @org.jetbrains.annotations.Nullable()
    java.lang.String classDecoration, int rivalCount, int reverseRivalCount, int tier, int rating, int ratingByProblemsSum, int ratingByClass, int ratingBySolvedCount, int ratingByVoteCount, int maxStreak, int rank, boolean isRival, boolean isReverseRival) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    public SolvedAcUser(@org.jetbrains.annotations.NotNull()
    java.lang.String handle, @org.jetbrains.annotations.Nullable()
    java.lang.String bio, @org.jetbrains.annotations.Nullable()
    java.lang.String badgeId, @org.jetbrains.annotations.Nullable()
    java.lang.String backgroundId, @org.jetbrains.annotations.Nullable()
    java.lang.String profileImageUrl, int solvedCount, int voteCount, long exp, int p8_47371452, @org.jetbrains.annotations.Nullable()
    java.lang.String classDecoration, int rivalCount, int reverseRivalCount, int tier, int rating, int ratingByProblemsSum, int ratingByClass, int ratingBySolvedCount, int ratingByVoteCount, int maxStreak, int rank, boolean isRival, boolean isReverseRival) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHandle() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBio() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBadgeId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBackgroundId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getProfileImageUrl() {
        return null;
    }
    
    public final int getSolvedCount() {
        return 0;
    }
    
    public final int getVoteCount() {
        return 0;
    }
    
    public final long getExp() {
        return 0L;
    }
    
    public final int getClass() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getClassDecoration() {
        return null;
    }
    
    public final int getRivalCount() {
        return 0;
    }
    
    public final int getReverseRivalCount() {
        return 0;
    }
    
    public final int getTier() {
        return 0;
    }
    
    public final int getRating() {
        return 0;
    }
    
    public final int getRatingByProblemsSum() {
        return 0;
    }
    
    public final int getRatingByClass() {
        return 0;
    }
    
    public final int getRatingBySolvedCount() {
        return 0;
    }
    
    public final int getRatingByVoteCount() {
        return 0;
    }
    
    public final int getMaxStreak() {
        return 0;
    }
    
    public final int getRank() {
        return 0;
    }
    
    public final boolean isRival() {
        return false;
    }
    
    public final boolean isReverseRival() {
        return false;
    }
}