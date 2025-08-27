package com.algoroadmap.infrastructure.external;

/**
 * solved.ac API 문제 정보 응답
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\"\b\u0086\b\u0018\u00002\u00020\u0001Bk\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\n\u0012\u0006\u0010\r\u001a\u00020\u0007\u0012\u0006\u0010\u000e\u001a\u00020\u0007\u0012\u0006\u0010\u000f\u001a\u00020\u0007\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013\u00a2\u0006\u0002\u0010\u0015J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0011H\u00c6\u0003J\u000f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u00c6\u0003J\t\u0010)\u001a\u00020\u0005H\u00c6\u0003J\t\u0010*\u001a\u00020\u0007H\u00c6\u0003J\t\u0010+\u001a\u00020\u0007H\u00c6\u0003J\t\u0010,\u001a\u00020\nH\u00c6\u0003J\t\u0010-\u001a\u00020\nH\u00c6\u0003J\t\u0010.\u001a\u00020\nH\u00c6\u0003J\t\u0010/\u001a\u00020\u0007H\u00c6\u0003J\t\u00100\u001a\u00020\u0007H\u00c6\u0003J\u0087\u0001\u00101\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\n2\b\b\u0002\u0010\r\u001a\u00020\u00072\b\b\u0002\u0010\u000e\u001a\u00020\u00072\b\b\u0002\u0010\u000f\u001a\u00020\u00072\b\b\u0002\u0010\u0010\u001a\u00020\u00112\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u00c6\u0001J\u0013\u00102\u001a\u00020\u00072\b\u00103\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00104\u001a\u00020\nH\u00d6\u0001J\t\u00105\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u000e\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u000f\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u001bR\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u001bR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u001bR\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\r\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001bR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0011\u0010\f\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0017\u00a8\u00066"}, d2 = {"Lcom/algoroadmap/infrastructure/external/SolvedAcProblem;", "", "problemId", "", "titleKo", "", "isSolvable", "", "isPartial", "acceptedUserCount", "", "level", "votedUserCount", "sprout", "givesNoRating", "isLevelLocked", "averageTries", "", "tags", "", "Lcom/algoroadmap/infrastructure/external/SolvedAcTag;", "(JLjava/lang/String;ZZIIIZZZDLjava/util/List;)V", "getAcceptedUserCount", "()I", "getAverageTries", "()D", "getGivesNoRating", "()Z", "getLevel", "getProblemId", "()J", "getSprout", "getTags", "()Ljava/util/List;", "getTitleKo", "()Ljava/lang/String;", "getVotedUserCount", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "infrastructure"})
public final class SolvedAcProblem {
    private final long problemId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String titleKo = null;
    private final boolean isSolvable = false;
    private final boolean isPartial = false;
    private final int acceptedUserCount = 0;
    private final int level = 0;
    private final int votedUserCount = 0;
    private final boolean sprout = false;
    private final boolean givesNoRating = false;
    private final boolean isLevelLocked = false;
    private final double averageTries = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.algoroadmap.infrastructure.external.SolvedAcTag> tags = null;
    
    public final long component1() {
        return 0L;
    }
    
    public final boolean component10() {
        return false;
    }
    
    public final double component11() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.algoroadmap.infrastructure.external.SolvedAcTag> component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final boolean component8() {
        return false;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.algoroadmap.infrastructure.external.SolvedAcProblem copy(long problemId, @org.jetbrains.annotations.NotNull()
    java.lang.String titleKo, boolean isSolvable, boolean isPartial, int acceptedUserCount, int level, int votedUserCount, boolean sprout, boolean givesNoRating, boolean isLevelLocked, double averageTries, @org.jetbrains.annotations.NotNull()
    java.util.List<com.algoroadmap.infrastructure.external.SolvedAcTag> tags) {
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
    
    public SolvedAcProblem(long problemId, @org.jetbrains.annotations.NotNull()
    java.lang.String titleKo, boolean isSolvable, boolean isPartial, int acceptedUserCount, int level, int votedUserCount, boolean sprout, boolean givesNoRating, boolean isLevelLocked, double averageTries, @org.jetbrains.annotations.NotNull()
    java.util.List<com.algoroadmap.infrastructure.external.SolvedAcTag> tags) {
        super();
    }
    
    public final long getProblemId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitleKo() {
        return null;
    }
    
    public final boolean isSolvable() {
        return false;
    }
    
    public final boolean isPartial() {
        return false;
    }
    
    public final int getAcceptedUserCount() {
        return 0;
    }
    
    public final int getLevel() {
        return 0;
    }
    
    public final int getVotedUserCount() {
        return 0;
    }
    
    public final boolean getSprout() {
        return false;
    }
    
    public final boolean getGivesNoRating() {
        return false;
    }
    
    public final boolean isLevelLocked() {
        return false;
    }
    
    public final double getAverageTries() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.algoroadmap.infrastructure.external.SolvedAcTag> getTags() {
        return null;
    }
}