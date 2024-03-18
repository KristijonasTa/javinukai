import { useQuery } from "@tanstack/react-query";
import { Link } from "react-router-dom";
import getLatestContestThumbnails from "../services/contests/getLatestContestThumbnails";

function ImageContainer({ imageUrl }) {
  return (
    <div className="text backdrop">
      <img className="col-span-1 row-span-1 xl:h-60 xl:w-60" src={imageUrl} />
    </div>
  );
}

function LandingPage() {
  const { data } = useQuery({
    queryKey: ["latestThumbnails"],
    queryFn: getLatestContestThumbnails,
  });

  return (
    <div className="w-full h-[82vh] bg-slate-800 relative flex justify-left pl-56 items-center">
      <div className="xl:grid xl:grid-cols-3 xl:grid-rows-3 xl:gap-4 border-r-2 pr-12 mr-12">
        {data?.map(
          (image, i) =>
            i < 10 && <ImageContainer key={"image" + i} imageUrl={image} />
        )}
      </div>
      <div className="flex flex-col space-y-12">
        <h1 className="text-5xl w-12 leading-normal font-bold text-white">
          Lithuanian Press Photography
        </h1>
        <Link
          className="text-3xl text-white hover:translate-x-5 transition"
          to="/contests"
        >
          Contests {"➡️"}
        </Link>
      </div>
    </div>
  );
}

export default LandingPage;