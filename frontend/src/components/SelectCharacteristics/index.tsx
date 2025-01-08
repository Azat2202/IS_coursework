import {useGenerateFactQuery} from "../../store/types.generated";
import {useState} from "react";

interface characteristicChooseProps {
    options: string[] | undefined,
    selectedId: number,
    onSelect: (id: number) => void,
}

function CharacteristicsChoose({options, selectedId, onSelect}: characteristicChooseProps) {
    return <div className={"flex flex-col"}>
        {options?.map(((option, index) =>
                <div className={index === selectedId ? "" : "bg-green-700"}
                     onClick={() => onSelect(index)}
                >{option}</div>
        ))}
    </div>
}

export function SelectCharacteristics() {
    //todo roomId
    const {data: characteristics} = useGenerateFactQuery({roomId: -1})
    const [bodyTypeId, setBodyTypeId] = useState(1)
    const [healthId, setHealthId] = useState(1)
    const [traitId, setTraitId] = useState(1)
    const [hobbyId, setHobbyId] = useState(1)
    const [professionId, setProfessionId] = useState(1)
    const [phobiaId, setPhobiaId] = useState(1)
    const [equipmentId, setEquipmentId] = useState(1)
    const [bagId, setBagId] = useState(1)

    function validateCharacteristics() {
        const levelSum = [
            characteristics?.bodyTypes?.at(bodyTypeId)?.level,
            characteristics?.healths?.at(healthId)?.level,
            characteristics?.traits?.at(traitId)?.level,
            characteristics?.hobbies?.at(hobbyId)?.level,
            characteristics?.professions?.at(professionId)?.level,
            characteristics?.phobiases?.at(phobiaId)?.level,
            characteristics?.equipments?.at(equipmentId)?.level,
            characteristics?.bags?.at(bagId)?.level
        ].map(v => v ?? 0).reduce((a, b) => a + b, 0)
    }

    function selectCharacteristics() {

    }

    return <>
        <CharacteristicsChoose options={characteristics?.bodyTypes?.map(b => b.name ?? "СЕКРЕТ)")}
                               selectedId={bodyTypeId}
                               onSelect={setBodyTypeId}/>
        <CharacteristicsChoose options={characteristics?.healths?.map(b => b.name ?? "СЕКРЕТ)")}
                               selectedId={healthId}
                               onSelect={setHealthId}/>
        <CharacteristicsChoose options={characteristics?.traits?.map(b => b.name ?? "СЕКРЕТ)")}
                               selectedId={traitId}
                               onSelect={setTraitId}/>
        <CharacteristicsChoose options={characteristics?.hobbies?.map(b => b.name ?? "СЕКРЕТ)")}
                               selectedId={hobbyId}
                               onSelect={setHobbyId}/>
        <CharacteristicsChoose options={characteristics?.professions?.map(b => b.name ?? "СЕКРЕТ)")}
                               selectedId={professionId}
                               onSelect={setProfessionId}/>
        <CharacteristicsChoose options={characteristics?.phobiases?.map(b => b.name ?? "СЕКРЕТ)")}
                               selectedId={phobiaId}
                               onSelect={setPhobiaId}/>
        <CharacteristicsChoose options={characteristics?.equipments?.map(b => b.name ?? "СЕКРЕТ)")}
                               selectedId={equipmentId}
                               onSelect={setEquipmentId}/>
        <CharacteristicsChoose options={characteristics?.bags?.map(b => b.name ?? "СЕКРЕТ)")}
                               selectedId={bagId}
                               onSelect={setBagId}/>
        <button onClick={selectCharacteristics}>Выбрать характеристики</button>
    </>
}