import {useCreateCharacterMutation, useGenerateFactQuery} from "../../store/types.generated";
import {useState} from "react";
import toast from "react-hot-toast";
import {useParams} from "react-router-dom";

interface characteristicChooseProps {
    options: string[] | undefined,
    selectedId: number,
    onSelect: (id: number) => void,
}

function CharacteristicsChoose({options, selectedId, onSelect}: characteristicChooseProps) {
    return <div className={"flex flex-col"}>
        {options?.map(((option, index) =>
                <div className={index === selectedId ? "bg-green-700" : ""}
                     onClick={() => onSelect(index)}
                >{option}</div>
        ))}
    </div>
}

export function SelectCharacteristics() {
    const { roomId: roomIdStr } = useParams<{ roomId: string }>()
    const roomId = Number(roomIdStr)
    const {data: characteristics} = useGenerateFactQuery({roomId: roomId})
    const [saveCharacteristics] = useCreateCharacterMutation()
    const [name, setName] = useState("")
    const [bodyTypeId, setBodyTypeId] = useState(1)
    const [healthId, setHealthId] = useState(1)
    const [traitId, setTraitId] = useState(1)
    const [hobbyId, setHobbyId] = useState(1)
    const [professionId, setProfessionId] = useState(1)
    const [phobiaId, setPhobiaId] = useState(1)
    const [equipmentId, setEquipmentId] = useState(1)
    const [bagId, setBagId] = useState(1)

    const characteristicsList = [
        {value: characteristics?.bodyTypes, selectedId: bodyTypeId, setter: setBodyTypeId},
        {value: characteristics?.healths, selectedId: healthId, setter: setHealthId},
        {value: characteristics?.traits, selectedId: traitId, setter: setTraitId},
        {value: characteristics?.hobbies, selectedId: hobbyId, setter: setHobbyId},
        {value: characteristics?.professions, selectedId: professionId, setter: setProfessionId},
        {value: characteristics?.phobiases, selectedId: phobiaId, setter: setPhobiaId},
        {value: characteristics?.equipments, selectedId: equipmentId, setter: setEquipmentId},
        {value: characteristics?.bags, selectedId: bagId, setter: setBagId}
    ].map(({value, selectedId, setter}) => {
        return {
            value: value ?? [],
            selectedId: selectedId,
            setter: setter
        }
    })

    function validateCharacteristics() {
        const levelSum = characteristicsList
            .map(({value, selectedId}) => value.at(selectedId)?.level ?? 0)
            .reduce((a, b) => a + b, 0)
        return levelSum === 0
    }

    async function selectCharacteristics() {
        await saveCharacteristics({
            createCharacterRequest: {
                    name: name,
                    age: 25,
                    sex: "MALE",
                    notes: "",
                    isActive: true,
                    bodyTypeId: bodyTypeId,
                    healthId: healthId,
                    traitId: traitId,
                    hobbyId: hobbyId,
                    professionId: professionId,
                    phobiaId: phobiaId,
                    equipmentId: equipmentId,
                    bagId: bagId,
                    roomId: roomId
            }}).unwrap()
            .then(() => toast.success("Персонаж сохранен!"))
            .catch(e => toast.error("Создать персонажа не удалось"))
    }

    return <>
        <label>
            Имя: <input type={"text"} value={name} onChange={e => setName(e.target.value)}/>
        </label>
        {characteristicsList.map(characteristic =>
            <CharacteristicsChoose options={characteristic.value.map(v => v.name ?? "СЕКРЕТ)")}
                                   selectedId={characteristic.selectedId}
                                   onSelect={characteristic.setter}/>
        )}
        <button onClick={selectCharacteristics}
                className={"disabled:opacity-20 disabled:bg-red-500 bg-green-700"}
                disabled={validateCharacteristics()}
        >Выбрать характеристики
        </button>
    </>
}