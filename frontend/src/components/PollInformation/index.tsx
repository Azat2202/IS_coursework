import {
    PollMessage,
    RoomMessage,
    useCreatePoolMutation,
    useGetPollsQuery,
    User,
    useVoteMutation
} from "../../store/types.generated";
import {CSSTransition} from "react-transition-group";
import {useEffect, useState} from "react";
import toast from "react-hot-toast";
import "./PollResults.css";
import {appConfig} from "../../index";

function ShowPoll({poll, roomData, user}: { poll: PollMessage, roomData?: RoomMessage, user: User }) {
    const liveCharacters = roomData?.characters?.filter(character => character?.isActive)
    const [selectedCharacterId, setSelectedCharacterId] = useState<number | null>(null);
    const [vote] = useVoteMutation();
    const handleChoose = (characterId: number) => {
        setSelectedCharacterId(characterId);
    };

    const handleVote = () => {
        if (selectedCharacterId !== null) {
            vote({roomId: roomData?.id!!, characterId: selectedCharacterId}).unwrap()
                .then(() => toast.success("Голос учтен"))
                .catch(() => toast.error("Ошибка голосования"));
        }
    };

    const estimatedTimeSeconds = (
        (new Date(poll.creationTime!!).getTime() + 1000 * appConfig["pollTime"] - Date.now()) / 1000).toFixed(0)

    return <div>
        <p>ОПРОС Раунд {poll.roundNumber}</p>
        <p>Осталось: {estimatedTimeSeconds} секунд</p>
        {liveCharacters?.filter(character => character?.user?.id !== user.id).map(character => (
            <div key={character?.id}>
                <input
                    type="radio"
                    id={`character-${character?.id}`}
                    name="character"
                    value={character?.id}
                    onChange={() => handleChoose(character?.id!!)}
                    checked={selectedCharacterId === character?.id}
                />
                <label
                    htmlFor={`character-${character?.id}`}>{character?.name ?? character?.user?.username}</label>
            </div>
        ))}
        <button onClick={handleVote} disabled={selectedCharacterId === null}>
            Проголосовать
        </button>
    </div>
}

function getKeyAndCount<T>(arr: T[]) {
    {
        return arr.reduce((accumulator, value) => {
            accumulator.set(value, ((accumulator.get(value) || 0) + 1));
            return accumulator;
        }, new Map<T, number>());
    }
}

export function PollResults({polls, roomData}: { polls: PollMessage[], roomData?: RoomMessage }) {
    const [showResults, setShowResults] = useState(false);
    const liveCharacters = roomData?.characters?.filter(character => character?.isActive);

    const targetAndVoteCount = (id: number) => Array.from(
        getKeyAndCount(
            polls.at(id)?.votes?.map(vote =>
                vote.targetCharacter?.name) ?? [])
    )
    return (
        <div>
            <button onClick={() => setShowResults(!showResults)}>
                {showResults ? "Скрыть результаты прошлых опросов" : "Показать результаты прошлых опросов"}
            </button>
            <CSSTransition
                in={showResults}
                timeout={1000}
                classNames="results"
                unmountOnExit
            >
                <div>
                    {polls.map((poll, id) =>
                        <>
                            <h3>Результаты опроса раунд {poll.roundNumber}</h3>
                            {targetAndVoteCount(id).map((targetCharacter, id) => (
                                    <p>
                                        {targetCharacter[0]} - {targetCharacter[1]} голосов
                                    </p>
                                ))}
                        </>
                    )}
                </div>
            </CSSTransition>
        </div>
    );
}

export function PollInformation({roomData, canOpen, user}: { roomData?: RoomMessage, canOpen: boolean, user: User }) {
    const [createPoll] = useCreatePoolMutation()
    const {data: polls, refetch: refetchPolls} = useGetPollsQuery({roomId: roomData?.id!!})
    const openPoll = polls?.find(poll => poll.isOpen)

    useEffect(() => {
        const intervalId = setInterval(refetchPolls, 200)
        return () => clearInterval(intervalId)
    }, [refetchPolls])

    async function createPollHandler() {
        await createPoll({roomId: roomData?.id!!}).unwrap()
            .then(() => toast.success("Опрос начат"))
            .catch(() => toast.error("Вы не можете начать опрос"))
    }

    // const isAdmin = roomData?.characters?.at(-1)?.user?.id === user.id
    return <div className={"text-burgundy-200"}>
        {canOpen && <button onClick={createPollHandler}>НАЧАТЬ ОПРОС</button>}
        {openPoll && <ShowPoll poll={openPoll} roomData={roomData} user={user}/>}
        {polls && <PollResults polls={polls}/>}
    </div>
}