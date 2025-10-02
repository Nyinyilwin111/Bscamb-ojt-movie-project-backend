package ojt_final_project.demo.dto;

//import lombok.Getter;
//import lombok.Setter;

//@Getter
//@Setter
public class MovieLikeCountDTO {
	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(long likeCount) {
		this.likeCount = likeCount;
	}

	private int movieId;
    private long likeCount;
    
    public MovieLikeCountDTO(int movieId, long likeCount) {
        this.movieId = movieId;
        this.likeCount = likeCount;
    }
}
